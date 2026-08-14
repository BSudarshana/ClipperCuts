package com.clippercuts.dto;

import com.clippercuts.entity.Payment;
import com.clippercuts.entity.Invoice;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class PaymentResponse {
    private final Integer id, invoiceId, paymentmethodId;
    private final String receiptnumber, invoicenumber, invoiceType, customerName, customerMobile, paymentmethodName,
            remarks, receivedByUsername;
    private final Timestamp paymentDate, invoiceDate;
    private final BigDecimal amount, invoiceAmount, paidAmount, balance;

    public PaymentResponse(Payment p, BigDecimal paid) {
        id = p.getId();
        Invoice i = p.getInvoice();
        invoiceId = i.getId();
        paymentmethodId = p.getPaymentmethod().getId();
        receiptnumber = p.getReceiptnumber();
        invoicenumber = i.getInvoicenumber();
        invoiceType = i.getInvoicetype();
        invoiceDate = i.getInvoicedate();
        if (i.getCustomer() != null) {
            customerName = i.getCustomer().getFullname();
            customerMobile = i.getCustomer().getMobile();
        } else if (i.getAppointment() != null && i.getAppointment().getCustomer() != null) {
            customerName = i.getAppointment().getCustomer().getFullname();
            customerMobile = i.getAppointment().getCustomer().getMobile();
        } else {
            customerName = "Walk-in Customer";
            customerMobile = null;
        }
        paymentmethodName = p.getPaymentmethod().getName();
        remarks = p.getRemarks();
        receivedByUsername = p.getReceivedByUser() == null ? null : p.getReceivedByUser().getUsername();
        paymentDate = p.getPaymentDate();
        amount = p.getAmount();
        invoiceAmount = i.getFinalAmount();
        paidAmount = paid;
        balance = invoiceAmount.subtract(paid).max(BigDecimal.ZERO);
    }

    public Integer getId() {
        return id;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public Integer getPaymentmethodId() {
        return paymentmethodId;
    }

    public String getReceiptnumber() {
        return receiptnumber;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public String getPaymentmethodName() {
        return paymentmethodName;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getReceivedByUsername() {
        return receivedByUsername;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
