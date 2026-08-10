package com.clippercuts.dto;

import com.clippercuts.entity.Transferitem;

import java.math.BigDecimal;

public class StockTransferItemResponse {

    private Integer id;
    private Integer itemId;
    private String itemnumber;
    private String itemName;
    private BigDecimal quantity;

    public static StockTransferItemResponse from(Transferitem line) {
        StockTransferItemResponse response =
                new StockTransferItemResponse();

        response.id = line.getId();
        response.itemId = line.getItem().getId();
        response.itemnumber = line.getItem().getItemnumber();
        response.itemName = line.getItem().getName();
        response.quantity = line.getQuantity();

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

    public BigDecimal getQuantity() {
        return quantity;
    }
}