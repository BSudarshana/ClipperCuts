package com.clippercuts.report.entity;

public class CountByItemCategory {

    private String category;
    private Long count;
    private Double percentage;

    public CountByItemCategory(String category, Long count) {
        this.category = category;
        this.count = count;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
