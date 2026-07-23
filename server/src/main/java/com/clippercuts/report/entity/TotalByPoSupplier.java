package com.clippercuts.report.entity;

import java.math.BigDecimal;

public class TotalByPoSupplier {

    private String supplier;
    private BigDecimal totalAmount;
    private Double percentage;

    public TotalByPoSupplier(String supplier, BigDecimal totalAmount) {
        this.supplier = supplier;
        this.totalAmount = totalAmount;
    }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
