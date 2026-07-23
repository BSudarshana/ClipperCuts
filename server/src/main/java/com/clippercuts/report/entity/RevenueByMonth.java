package com.clippercuts.report.entity;

import java.math.BigDecimal;

public class RevenueByMonth {

    private Integer year;
    private Integer month;
    private BigDecimal totalAmount;

    public RevenueByMonth(Integer year, Integer month, BigDecimal totalAmount) {
        this.year = year;
        this.month = month;
        this.totalAmount = totalAmount;
    }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getMonth() { return month; }
    public void setMonth(Integer month) { this.month = month; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
