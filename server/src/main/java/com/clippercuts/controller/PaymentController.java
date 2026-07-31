package com.clippercuts.controller;

import com.clippercuts.dao.InvoiceDao;
import com.clippercuts.dao.PaymentDao;
import com.clippercuts.dao.PaymentStatusDao;
import com.clippercuts.dao.PaymentmethodDao;
import com.clippercuts.dto.PaymentCreateRequest;
import com.clippercuts.entity.Invoice;
import com.clippercuts.entity.Payment;
import com.clippercuts.entity.Paymentmethod;
import com.clippercuts.entity.Paymentstatus;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentDao paymentDao;
    private final InvoiceDao invoiceDao;
    private final PaymentmethodDao paymentmethodDao;
    private final PaymentStatusDao paymentStatusDao;

    public PaymentController(PaymentDao paymentDao,
                             InvoiceDao invoiceDao,
                             PaymentmethodDao paymentmethodDao,
                             PaymentStatusDao paymentStatusDao) {
        this.paymentDao = paymentDao;
        this.invoiceDao = invoiceDao;
        this.paymentmethodDao = paymentmethodDao;
        this.paymentStatusDao = paymentStatusDao;
    }

    @GetMapping(produces = "application/json")
    public List<Payment> get(@RequestParam HashMap<String, String> params) {
        Stream<Payment> stream = paymentDao.findAll().stream();
        String receiptNumber = params.get("receiptnumber");

        if (receiptNumber != null && !receiptNumber.trim().isEmpty()) {
            String search = receiptNumber.trim().toLowerCase();
            stream = stream.filter(payment ->
                    payment.getReceiptnumber() != null &&
                            payment.getReceiptnumber().toLowerCase().contains(search));
        }
        return stream.collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Integer id) {
        return paymentDao.findDetailedById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment does not exist"));
    }

    @GetMapping("/unpaid-invoices")
    public List<Invoice> getUnpaidInvoices() {
        return invoiceDao.findByPaymentstatus_NameIgnoreCase("Unpaid")
                .stream()
                .filter(invoice -> !paymentDao.existsByInvoice_Id(invoice.getId()))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public HashMap<String, String> add(@Valid @RequestBody PaymentCreateRequest request) {
        Invoice invoice = invoiceDao.findDetailedById(request.getInvoiceId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Invoice does not exist"));

        if (invoice.getPaymentstatus() == null ||
                !"Unpaid".equalsIgnoreCase(invoice.getPaymentstatus().getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Only Unpaid invoices can be paid");
        }

        if (paymentDao.existsByInvoice_Id(invoice.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "This invoice already has a payment");
        }

        BigDecimal amount = request.getAmount().setScale(2);
        BigDecimal invoiceAmount = invoice.getFinalAmount().setScale(2);
        if (amount.compareTo(invoiceAmount) != 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Payment amount must equal the invoice final amount");
        }

        Paymentmethod method = paymentmethodDao.findById(request.getPaymentmethodId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Payment method does not exist"));

        Paymentstatus paid = paymentStatusDao.findByNameIgnoreCase("Paid");
        if (paid == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Payment status 'Paid' is not configured");
        }

        Payment payment = new Payment();
        payment.setReceiptnumber(nextReceiptNumber());
        payment.setPaymentDate(Timestamp.from(Instant.now()));
        payment.setAmount(invoiceAmount);
        payment.setRemarks(cleanRemarks(request.getRemarks()));
        payment.setInvoice(invoice);
        payment.setPaymentmethod(method);
        paymentDao.save(payment);

        invoice.setPaymentstatus(paid);
        invoiceDao.save(invoice);

        HashMap<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(payment.getId()));
        response.put("url", "/payments/" + payment.getId());
        response.put("errors", "");
        return response;
    }

    private String nextReceiptNumber() {
        int year = Year.now().getValue();
        String lastNumber = paymentDao.getLastReceiptByYear(year);
        int sequence = 1;

        if (lastNumber != null) {
            String[] parts = lastNumber.split("-");
            if (parts.length == 3) {
                try {
                    sequence = Integer.parseInt(parts[2]) + 1;
                } catch (NumberFormatException ignored) {
                    sequence = 1;
                }
            }
        }
        return String.format("REC-%d-%06d", year, sequence);
    }

    private String cleanRemarks(String remarks) {
        if (remarks == null) {
            return null;
        }
        String cleaned = remarks.trim();
        return cleaned.isEmpty() ? null : cleaned;
    }
}




