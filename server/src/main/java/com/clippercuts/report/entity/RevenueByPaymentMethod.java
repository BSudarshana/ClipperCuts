package com.clippercuts.report.entity;

import java.math.BigDecimal;

public class RevenueByPaymentMethod {

    private String method;
    private BigDecimal totalAmount;
    private Double percentage;

    public RevenueByPaymentMethod(String method, BigDecimal totalAmount) {
        this.method = method;
        this.totalAmount = totalAmount;
    }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
