package com.clippercuts.dto;

import com.clippercuts.entity.Invoice;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PayableInvoiceResponse {
    private final Integer id;
    private final String invoicenumber, invoicetype, customerName, customerMobile, paymentStatus;
    private final Timestamp invoicedate;
    private final BigDecimal finalAmount, paidAmount, balance;

    public PayableInvoiceResponse(Invoice invoice, BigDecimal paid) {
        id = invoice.getId();
        invoicenumber = invoice.getInvoicenumber();
        invoicetype = invoice.getInvoicetype();
        invoicedate = invoice.getInvoicedate();
        finalAmount = invoice.getFinalAmount();
        paidAmount = paid;
        balance = finalAmount.subtract(paid).max(BigDecimal.ZERO);
        paymentStatus = invoice.getPaymentstatus().getName();
        if (invoice.getCustomer() != null) {
            customerName = invoice.getCustomer().getFullname();
            customerMobile = invoice.getCustomer().getMobile();
        } else if (invoice.getAppointment() != null && invoice.getAppointment().getCustomer() != null) {
            customerName = invoice.getAppointment().getCustomer().getFullname();
            customerMobile = invoice.getAppointment().getCustomer().getMobile();
        } else {
            customerName = "Walk-in Customer";
            customerMobile = null;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public String getInvoicetype() {
        return invoicetype;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Timestamp getInvoicedate() {
        return invoicedate;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
