package com.clippercuts.report.entity;

public class CountByCustomerType {

    private String type;
    private Long count;
    private Double percentage;

    public CountByCustomerType(String type, Long count) {
        this.type = type;
        this.count = count;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
