package com.clippercuts.controller;

import com.clippercuts.dao.*;
import com.clippercuts.dto.InvoiceCreateRequest;
import com.clippercuts.dto.InvoiceResponse;
import com.clippercuts.entity.*;
import com.clippercuts.util.NumberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceDao invoiceDao;
    private final AppointmentDao appointmentDao;
    private final PaymentStatusDao paymentStatusDao;
    private final PromotionDao promotionDao;
    private final NumberService numberService;
    private final UserDao userDao;

    public InvoiceController(InvoiceDao invoiceDao,
                             AppointmentDao appointmentDao,
                             PaymentStatusDao paymentStatusDao,
                             PromotionDao promotionDao,
                             NumberService numberService, UserDao userDao) {
        this.invoiceDao = invoiceDao;
        this.appointmentDao = appointmentDao;
        this.paymentStatusDao = paymentStatusDao;
        this.promotionDao = promotionDao;
        this.numberService = numberService;
        this.userDao = userDao;
    }

    @GetMapping(produces = "application/json")
    public List<InvoiceResponse> get(@RequestParam HashMap<String, String> params) {
        Stream<Invoice> stream = invoiceDao.findAll().stream();
        String number = params.get("invoicenumber");
        String date = params.get("invoicedate");

        if (number != null && !number.trim().isEmpty()) {
            stream = stream.filter(i -> i.getInvoicenumber() != null &&
                    i.getInvoicenumber().toLowerCase().contains(number.trim().toLowerCase()));
        }
        if (date != null && !date.trim().isEmpty()) {
            stream = stream.filter(i -> i.getInvoicedate() != null &&
                    i.getInvoicedate().toString().startsWith(date.trim()));
        }
        return stream.map(InvoiceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/eligible-appointments")
    public List<Appointment> getEligibleAppointments() {
//        return appointmentDao.findByAppointmentstatus_NameIgnoreCase("Completed")
//                .stream()
//                .filter(a -> !invoiceDao.existsByAppointment_Id(a.getId()))
//                .collect(Collectors.toList());
        return appointmentDao.findEligibleForInvoice("Completed");
    }

    @GetMapping("/by-user")
    public List<InvoiceResponse> getByUser(@RequestParam Integer userId) {
        return invoiceDao.findByCreatedByUser_Id(userId).stream()
                .map(InvoiceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/my")
    public List<InvoiceResponse> getMyInvoices(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not authenticated"
            );
        }

        return invoiceDao.findByCreatedByUser_Username(authentication.getName())
                .stream()
                .map(InvoiceResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public HashMap<String, String> add(@Valid @RequestBody InvoiceCreateRequest request) {
        Appointment appointment = appointmentDao.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Appointment does not exist"));

        if (appointment.getAppointmentstatus() == null ||
                !"Completed".equalsIgnoreCase(appointment.getAppointmentstatus().getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Only Completed appointments can be invoiced");
        }

        if (invoiceDao.existsByAppointment_Id(appointment.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "This appointment already has an invoice");
        }

        BigDecimal total = appointment.getAppointmentservices().stream()
                .map(line -> line.getAgreedPrice() == null ? BigDecimal.ZERO : line.getAgreedPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "The appointment has no billable services");
        }

        BigDecimal discount = request.getDiscount() == null
                ? BigDecimal.ZERO : request.getDiscount();
        if (discount.compareTo(total) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Discount cannot exceed the total amount");
        }

        Paymentstatus unpaid = paymentStatusDao.findByNameIgnoreCase("Unpaid");
        if (unpaid == null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Payment status 'Unpaid' is not configured");
        }

        Promotion promotion = null;
        if (request.getPromotionId() != null) {
            promotion = promotionDao.findById(request.getPromotionId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Promotion does not exist"));
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User is not authenticated"
            );
        }

        String invoiceType = "SERVICE";

        String username = authentication.getName();
        User loggedUser = userDao.findByUsername(username);

        if (loggedUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Logged-in user was not found"
            );
        }



        Invoice invoice = new Invoice();
        invoice.setInvoicenumber(numberService.getLastInvoiceByYear());
        invoice.setInvoicedate(Timestamp.from(Instant.now()));
        invoice.setTotalamount(total);
        invoice.setDiscount(discount);
        invoice.setFinalAmount(total.subtract(discount));
        invoice.setPaymentstatus(unpaid);
        invoice.setAppointment(appointment);
        invoice.setPromotion(promotion);
        invoice.setInvoicetype(invoiceType);
        invoice.setCreatedByUser(loggedUser);
//        invoice.setInvoicedate(Timestamp.valueOf(LocalDateTime.now()));

        invoiceDao.save(invoice);

        HashMap<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(invoice.getId()));
        response.put("url", "/invoices/" + invoice.getId());
        response.put("errors", "");
        return response;
    }

    @DeleteMapping("/{id}")
    public HashMap<String, String> delete(@PathVariable Integer id) {
        Invoice invoice = invoiceDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Invoice does not exist"));
        invoiceDao.delete(invoice);

        HashMap<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(id));
        response.put("url", "/invoices/" + id);
        response.put("errors", "");
        return response;
    }
}