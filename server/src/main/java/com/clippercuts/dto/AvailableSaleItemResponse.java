package com.clippercuts.dto;

import com.clippercuts.entity.ItemstockLocation;
import java.math.BigDecimal;

public class AvailableSaleItemResponse {
    private final Integer itemId;
    private final String itemnumber;
    private final String name;
    private final BigDecimal availableQuantity;
    private final BigDecimal sellingPrice;
    private final String unitType;

    public AvailableSaleItemResponse(ItemstockLocation stock) {
        itemId = stock.getItem().getId();
        itemnumber = stock.getItem().getItemnumber();
        name = stock.getItem().getName();
        availableQuantity = stock.getQuantity();
        sellingPrice = stock.getItem().getSprice();
        unitType = stock.getItem().getUnittype() == null ? "" : stock.getItem().getUnittype().getName();
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public String getUnitType() {
        return unitType;
    }
}
