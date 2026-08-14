package com.clippercuts.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductSaleRequest {
    private Integer customerId;
    @NotNull
    private Integer locationId;
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal discount = BigDecimal.ZERO;
    @Valid
    @NotEmpty
    private List<ProductSaleItemRequest> items;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer v) {
        customerId = v;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer v) {
        locationId = v;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal v) {
        discount = v;
    }

    public List<ProductSaleItemRequest> getItems() {
        return items;
    }

    public void setItems(List<ProductSaleItemRequest> v) {
        items = v;
    }
}
