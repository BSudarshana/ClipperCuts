package com.clippercuts.dto;

import java.math.BigDecimal;

public class GrnPurchaseOrderItemResponse {
    private final Integer poItemId;
    private final Integer itemId;
    private final String itemnumber;
    private final String itemName;
    private final String unitType;
    private final BigDecimal orderedQuantity;
    private final BigDecimal previouslyReceivedQuantity;
    private final BigDecimal remainingQuantity;
    private final BigDecimal unitCost;

    public GrnPurchaseOrderItemResponse(Integer poItemId, Integer itemId, String itemnumber,
                                        String itemName, String unitType, BigDecimal orderedQuantity,
                                        BigDecimal previouslyReceivedQuantity, BigDecimal remainingQuantity,
                                        BigDecimal unitCost) {
        this.poItemId = poItemId; this.itemId = itemId; this.itemnumber = itemnumber;
        this.itemName = itemName; this.unitType = unitType; this.orderedQuantity = orderedQuantity;
        this.previouslyReceivedQuantity = previouslyReceivedQuantity;
        this.remainingQuantity = remainingQuantity; this.unitCost = unitCost;
    }
    public Integer getPoItemId() { return poItemId; }
    public Integer getItemId() { return itemId; }
    public String getItemnumber() { return itemnumber; }
    public String getItemName() { return itemName; }
    public String getUnitType() { return unitType; }
    public BigDecimal getOrderedQuantity() { return orderedQuantity; }
    public BigDecimal getPreviouslyReceivedQuantity() { return previouslyReceivedQuantity; }
    public BigDecimal getRemainingQuantity() { return remainingQuantity; }
    public BigDecimal getUnitCost() { return unitCost; }
}
