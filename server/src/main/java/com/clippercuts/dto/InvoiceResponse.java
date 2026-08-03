package com.clippercuts.dto;

import com.clippercuts.entity.Appointment;
import com.clippercuts.entity.Invoice;
import com.clippercuts.entity.Paymentstatus;
import com.clippercuts.entity.Promotion;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Safe API representation of an invoice. It deliberately does not expose the
 * complete User entity because User contains password, salt and role data.
 */
public class InvoiceResponse {

    private Integer id;
    private String invoicenumber;
    private Timestamp invoicedate;
    private BigDecimal totalamount;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    private String invoicetype;
    private Paymentstatus paymentstatus;
    private Appointment appointment;
    private Promotion promotion;
    private Integer createdByUserId;
    private String createdByUsername;

    public static InvoiceResponse fromEntity(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setInvoicenumber(invoice.getInvoicenumber());
        response.setInvoicedate(invoice.getInvoicedate());
        response.setTotalamount(invoice.getTotalamount());
        response.setDiscount(invoice.getDiscount());
        response.setFinalAmount(invoice.getFinalAmount());
        response.setInvoicetype(invoice.getInvoicetype());
        response.setPaymentstatus(invoice.getPaymentstatus());
        response.setAppointment(invoice.getAppointment());
        response.setPromotion(invoice.getPromotion());

        if (invoice.getCreatedByUser() != null) {
            response.setCreatedByUserId(invoice.getCreatedByUser().getId());
            response.setCreatedByUsername(invoice.getCreatedByUser().getUsername());
        }

        return response;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getInvoicenumber() { return invoicenumber; }
    public void setInvoicenumber(String invoicenumber) { this.invoicenumber = invoicenumber; }
    public Timestamp getInvoicedate() { return invoicedate; }
    public void setInvoicedate(Timestamp invoicedate) { this.invoicedate = invoicedate; }
    public BigDecimal getTotalamount() { return totalamount; }
    public void setTotalamount(BigDecimal totalamount) { this.totalamount = totalamount; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public BigDecimal getFinalAmount() { return finalAmount; }
    public void setFinalAmount(BigDecimal finalAmount) { this.finalAmount = finalAmount; }
    public String getInvoicetype() { return invoicetype; }
    public void setInvoicetype(String invoicetype) { this.invoicetype = invoicetype; }
    public Paymentstatus getPaymentstatus() { return paymentstatus; }
    public void setPaymentstatus(Paymentstatus paymentstatus) { this.paymentstatus = paymentstatus; }
    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
    public Promotion getPromotion() { return promotion; }
    public void setPromotion(Promotion promotion) { this.promotion = promotion; }
    public Integer getCreatedByUserId() { return createdByUserId; }
    public void setCreatedByUserId(Integer createdByUserId) { this.createdByUserId = createdByUserId; }
    public String getCreatedByUsername() { return createdByUsername; }
    public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; }
}
