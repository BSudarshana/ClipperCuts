package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "expense_number", nullable = false, unique = true, length = 20)
    private String expenseNumber;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "expensecategory_id", nullable = false)
    private Expensecategory expensecategory;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentmethod_id", nullable = false)
    private Paymentmethod paymentmethod;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paid_by_user_id", nullable = false)
    private User paidByUser;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Transient
    public String getPaidByUsername() {
        return paidByUser == null ? null : paidByUser.getUsername();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getExpenseNumber() { return expenseNumber; }
    public void setExpenseNumber(String expenseNumber) { this.expenseNumber = expenseNumber; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Expensecategory getExpensecategory() { return expensecategory; }
    public void setExpensecategory(Expensecategory expensecategory) { this.expensecategory = expensecategory; }
    public Paymentmethod getPaymentmethod() { return paymentmethod; }
    public void setPaymentmethod(Paymentmethod paymentmethod) { this.paymentmethod = paymentmethod; }
    public User getPaidByUser() { return paidByUser; }
    public void setPaidByUser(User paidByUser) { this.paidByUser = paidByUser; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
