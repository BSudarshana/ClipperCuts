package com.clippercuts.dto;

import com.clippercuts.entity.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceResponse {
    private Integer id, createdByUserId, customerId;
    private String invoicenumber, invoicetype, createdByUsername, customerName, customerMobile;
    private Timestamp invoicedate;
    private BigDecimal totalamount, discount, finalAmount;
    private Paymentstatus paymentstatus;
    private Appointment appointment;
    private Promotion promotion;
    private List<InvoiceItemResponse> invoiceItems;

    public static InvoiceResponse fromEntity(Invoice i) {
        InvoiceResponse r = new InvoiceResponse();
        r.id = i.getId();
        r.invoicenumber = i.getInvoicenumber();
        r.invoicedate = i.getInvoicedate();
        r.totalamount = i.getTotalamount();
        r.discount = i.getDiscount();
        r.finalAmount = i.getFinalAmount();
        r.invoicetype = i.getInvoicetype();
        r.paymentstatus = i.getPaymentstatus();
        r.appointment = i.getAppointment();
        r.promotion = i.getPromotion();
        Customer c = i.getCustomer() != null ? i.getCustomer()
                : (i.getAppointment() != null ? i.getAppointment().getCustomer() : null);
        if (c != null) {
            r.customerId = c.getId();
            r.customerName = c.getFullname();
            r.customerMobile = c.getMobile();
        }
        if (i.getCreatedByUser() != null) {
            r.createdByUserId = i.getCreatedByUser().getId();
            r.createdByUsername = i.getCreatedByUser().getUsername();
        }
        r.invoiceItems = i.getInvoiceItems() == null ? Collections.emptyList()
                : i.getInvoiceItems().stream().map(InvoiceItemResponse::new).collect(Collectors.toList());
        return r;
    }

    public Integer getId() {
        return id;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public Timestamp getInvoicedate() {
        return invoicedate;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public String getInvoicetype() {
        return invoicetype;
    }

    public Paymentstatus getPaymentstatus() {
        return paymentstatus;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public List<InvoiceItemResponse> getInvoiceItems() {
        return invoiceItems;
    }
}
