package com.clippercuts.dto;

import com.clippercuts.entity.Stockwriteoffitem;

import java.math.BigDecimal;

public class StockWriteOffItemResponse {

    private Integer id;
    private Integer itemId;
    private String itemnumber;
    private String itemName;
    private String unitType;
    private BigDecimal quantity;

    public static StockWriteOffItemResponse from(Stockwriteoffitem item) {
        StockWriteOffItemResponse response = new StockWriteOffItemResponse();
        response.id = item.getId();
        response.itemId = item.getItem().getId();
        response.itemnumber = item.getItem().getItemnumber();
        response.itemName = item.getItem().getName();
        response.unitType = item.getItem().getUnittype() == null
                ? ""
                : item.getItem().getUnittype().getName();
        response.quantity = item.getQuantity();
        return response;
    }

    public Integer getId() {
        return id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUnitType() {
        return unitType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
