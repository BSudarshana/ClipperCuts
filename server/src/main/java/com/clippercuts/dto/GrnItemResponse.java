package com.clippercuts.dto;

import com.clippercuts.entity.GrnItem;
import java.math.BigDecimal;

public class GrnItemResponse {
    private final Integer id; private final Integer itemId; private final String itemnumber;
    private final String itemName; private final BigDecimal quantity;
    private final BigDecimal unitCost; private final BigDecimal subTotal;
    public GrnItemResponse(GrnItem line) {
        id=line.getId(); itemId=line.getItem().getId(); itemnumber=line.getItem().getItemnumber();
        itemName=line.getItem().getName(); quantity=line.getQuantity(); unitCost=line.getUnitcost(); subTotal=line.getSubTotal();
    }
    public Integer getId(){return id;} public Integer getItemId(){return itemId;}
    public String getItemnumber(){return itemnumber;} public String getItemName(){return itemName;}
    public BigDecimal getQuantity(){return quantity;} public BigDecimal getUnitCost(){return unitCost;}
    public BigDecimal getSubTotal(){return subTotal;}
}
