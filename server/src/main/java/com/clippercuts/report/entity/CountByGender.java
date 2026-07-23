package com.clippercuts.report.entity;

public class CountByGender {

    private String gender;
    private Long count;
    private Double percentage;

    public CountByGender(String gender, Long count) {
        this.gender = gender;
        this.count = count;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }

    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
}
