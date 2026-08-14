package com.clippercuts.dto;

import com.clippercuts.entity.ItemstockLocation;

import java.math.BigDecimal;

public class StockWriteOffAvailableItemResponse {

    private Integer itemId;
    private String itemnumber;
    private String name;
    private BigDecimal availableQuantity;
    private String unitType;

    public static StockWriteOffAvailableItemResponse from(ItemstockLocation stock) {
        StockWriteOffAvailableItemResponse response = new StockWriteOffAvailableItemResponse();
        response.itemId = stock.getItem().getId();
        response.itemnumber = stock.getItem().getItemnumber();
        response.name = stock.getItem().getName();
        response.availableQuantity = stock.getQuantity();
        response.unitType = stock.getItem().getUnittype() == null
                ? ""
                : stock.getItem().getUnittype().getName();
        return response;
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

    public String getUnitType() {
        return unitType;
    }
}
