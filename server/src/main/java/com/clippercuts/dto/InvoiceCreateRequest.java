package com.clippercuts.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class InvoiceCreateRequest {
    @NotNull
    private Integer appointmentId;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal discount = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal tax = BigDecimal.ZERO;

    private Integer promotionId;

    public Integer getAppointmentId() { return appointmentId; }
    public void setAppointmentId(Integer appointmentId) { this.appointmentId = appointmentId; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }
    public Integer getPromotionId() { return promotionId; }
    public void setPromotionId(Integer promotionId) { this.promotionId = promotionId; }
}
