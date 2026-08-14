package com.clippercuts.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payment_date", nullable = false)
    private Timestamp paymentDate;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(length = 255)
    private String remarks;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "paymentmethod_id")
    private Paymentmethod paymentmethod;

    @Column(nullable = false, unique = true, length = 45)
    private String receiptnumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "received_by_user_id")
    private User receivedByUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer v) {
        id = v;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp v) {
        paymentDate = v;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal v) {
        amount = v;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String v) {
        remarks = v;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice v) {
        invoice = v;
    }

    public Paymentmethod getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(Paymentmethod v) {
        paymentmethod = v;
    }

    public String getReceiptnumber() {
        return receiptnumber;
    }

    public void setReceiptnumber(String v) {
        receiptnumber = v;
    }

    public User getReceivedByUser() {
        return receivedByUser;
    }

    public void setReceivedByUser(User v) {
        receivedByUser = v;
    }
}
