package com.clippercuts.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Invoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "invoicenumber")
    private String invoicenumber;
    @Basic
    @Column(name = "invoicedate")
    private Timestamp invoicedate;
    @Basic
    @Column(name = "totalamount")
    private BigDecimal totalamount;
    @Basic
    @Column(name = "discount")
    private BigDecimal discount;
    @Basic
    @Column(name = "tax")
    private BigDecimal tax;
    @Basic
    @Column(name = "final_amount")
    private BigDecimal finalAmount;
    @OneToMany(mappedBy = "invoice")
    private Collection<PackageHasInvoice> packageHasInvoices;
    @ManyToOne
    @JoinColumn(name = "paymentstatus_id", referencedColumnName = "id", nullable = false)
    private Paymentstatus paymentstatus;
    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "promotion_id", referencedColumnName = "id", nullable = false)
    private Promotion promotion;
    @OneToMany(mappedBy = "invoice")
    private Collection<InvoiceItem> invoiceItems;
    @OneToMany(mappedBy = "invoice")
    private Collection<Payment> payments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getInvoicedate() {
        return invoicedate;
    }

    public void setInvoicedate(Timestamp invoicedate) {
        this.invoicedate = invoicedate;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(invoicedate, invoice.invoicedate) && Objects.equals(totalamount, invoice.totalamount) && Objects.equals(discount, invoice.discount) && Objects.equals(tax, invoice.tax) && Objects.equals(finalAmount, invoice.finalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoicedate, totalamount, discount, tax, finalAmount);
    }

    public Collection<PackageHasInvoice> getPackageHasInvoices() {
        return packageHasInvoices;
    }

    public void setPackageHasInvoices(Collection<PackageHasInvoice> packageHasInvoices) {
        this.packageHasInvoices = packageHasInvoices;
    }

    public Paymentstatus getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(Paymentstatus paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Collection<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Collection<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Collection<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Collection<Payment> payments) {
        this.payments = payments;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber;
    }
}
