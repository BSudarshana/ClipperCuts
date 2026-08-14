package com.clippercuts.controller;

import com.clippercuts.dao.*;
import com.clippercuts.dto.*;
import com.clippercuts.entity.*;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.math.*;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentDao paymentDao;
    private final InvoiceDao invoiceDao;
    private final PaymentmethodDao methodDao;
    private final PaymentStatusDao statusDao;
    private final UserDao userDao;

    public PaymentController(PaymentDao p, InvoiceDao i, PaymentmethodDao m, PaymentStatusDao s, UserDao u) {
        paymentDao = p;
        invoiceDao = i;
        methodDao = m;
        statusDao = s;
        userDao = u;
    }

    @GetMapping
    public List<PaymentResponse> all(@RequestParam Map<String, String> q) {
        String n = q.get("receiptnumber");
        return paymentDao.findAll().stream()
                .filter(p -> n == null || n.trim().isEmpty()
                        || p.getReceiptnumber().toLowerCase().contains(n.trim().toLowerCase()))
                .map(p -> new PaymentResponse(p, totalPaid(p.getInvoice().getId()))).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PaymentResponse one(@PathVariable Integer id) {
        Payment p = paymentDao.findDetailedById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment does not exist"));
        return new PaymentResponse(p, totalPaid(p.getInvoice().getId()));
    }

    @GetMapping("/payable-invoices")
    public List<PayableInvoiceResponse> payable() {
        return invoiceDao.findByPaymentstatus_NameIn(Arrays.asList("Unpaid", "Partially Paid")).stream()
                .map(i -> new PayableInvoiceResponse(i, totalPaid(i.getId())))
                .filter(i -> i.getBalance().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Map<String, String> add(@Valid @RequestBody PaymentCreateRequest r, Principal principal) {
        if (principal == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        User user = userDao.findByUsername(principal.getName());
        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Logged-in user not found");
        Invoice invoice = invoiceDao.findForPaymentUpdate(r.getInvoiceId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice does not exist"));
        BigDecimal paid = totalPaid(invoice.getId());
        BigDecimal balance = invoice.getFinalAmount().subtract(paid);
        BigDecimal amount = r.getAmount().setScale(2, RoundingMode.HALF_UP);
        if (balance.compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invoice is already fully paid");
        if (amount.compareTo(balance) > 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payment cannot exceed remaining balance " + balance);
        Paymentmethod method = methodDao.findById(r.getPaymentmethodId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method does not exist"));
        Payment p = new Payment();
        p.setReceiptnumber(nextReceipt());
        p.setPaymentDate(Timestamp.from(Instant.now()));
        p.setAmount(amount);
        p.setRemarks(clean(r.getRemarks()));
        p.setInvoice(invoice);
        p.setPaymentmethod(method);
        p.setReceivedByUser(user);
        paymentDao.save(p);
        BigDecimal newPaid = paid.add(amount);
        String statusName = newPaid.compareTo(invoice.getFinalAmount()) >= 0 ? "Paid" : "Partially Paid";
        Paymentstatus status = statusDao.findByNameIgnoreCase(statusName);
        if (status == null)
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Payment status '" + statusName + "' is not configured");
        invoice.setPaymentstatus(status);
        invoiceDao.save(invoice);
        Map<String, String> x = new HashMap<>();
        x.put("id", String.valueOf(p.getId()));
        x.put("receiptnumber", p.getReceiptnumber());
        x.put("message", "Payment recorded successfully");
        return x;
    }

    private BigDecimal totalPaid(Integer id) {
        BigDecimal x = paymentDao.totalPaid(id);
        return x == null ? BigDecimal.ZERO : x;
    }

    private String nextReceipt() {
        int y = Year.now().getValue();
        String last = paymentDao.getLastReceiptByYear(y);
        int n = last == null ? 1 : Integer.parseInt(last.split("-")[2]) + 1;
        return String.format("REC-%d-%06d", y, n);
    }

    private String clean(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim();
    }
}
