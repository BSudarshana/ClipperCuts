package com.clippercuts.dto;

import com.clippercuts.entity.InvoiceItem;
import java.math.BigDecimal;

public class InvoiceItemResponse {
    private final Integer id, itemId, locationId;
    private final String itemnumber, itemName, locationName;
    private final BigDecimal quantity, price, discount, subtotal;

    public InvoiceItemResponse(InvoiceItem x) {
        id = x.getId();
        itemId = x.getItem().getId();
        locationId = x.getLocation().getId();
        itemnumber = x.getItem().getItemnumber();
        itemName = x.getItem().getName();
        locationName = x.getLocation().getName();
        quantity = x.getQuantity();
        price = x.getPrice();
        discount = x.getDiscount();
        subtotal = x.getSubtotal();
    }

    public Integer getId() {
        return id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public String getItemName() {
        return itemName;
    }

    public String getLocationName() {
        return locationName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}
