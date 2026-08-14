package com.clippercuts.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductSaleItemRequest {
    @NotNull
    private Integer itemId;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal quantity;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer v) {
        itemId = v;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal v) {
        quantity = v;
    }
}
