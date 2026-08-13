package com.clippercuts.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GrnItemRequest {
    @NotNull private Integer poItemId;
    @NotNull @DecimalMin(value = "0.01") private BigDecimal receivedQuantity;

    public Integer getPoItemId() { return poItemId; }
    public void setPoItemId(Integer poItemId) { this.poItemId = poItemId; }
    public BigDecimal getReceivedQuantity() { return receivedQuantity; }
    public void setReceivedQuantity(BigDecimal receivedQuantity) { this.receivedQuantity = receivedQuantity; }
}
