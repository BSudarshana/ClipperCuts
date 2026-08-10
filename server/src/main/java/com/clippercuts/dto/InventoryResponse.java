package com.clippercuts.dto;

import com.clippercuts.entity.Item;
import com.clippercuts.entity.ItemstockLocation;
import com.clippercuts.entity.Location;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class InventoryResponse {
    private Integer id;
    private Integer itemId;
    private String itemnumber;
    private String itemName;
    private String brand;
    private String unitType;
    private String category;
    private String subcategory;
    private Integer rop;
    private Integer locationId;
    private String locationName;
    private String locationType;
    private BigDecimal quantity;
    private BigDecimal totalStock;
    private Timestamp lastupdate;
    private boolean lowStock;

    public static InventoryResponse from(ItemstockLocation stock, BigDecimal totalStock) {
        InventoryResponse response = new InventoryResponse();
        Item item = stock.getItem();
        Location location = stock.getLocation();

        response.id = stock.getId();
        response.itemId = item.getId();
        response.itemnumber = item.getItemnumber();
        response.itemName = item.getName();
        response.brand = item.getItembrand() == null ? null : item.getItembrand().getName();
        response.unitType = item.getUnittype() == null ? null : item.getUnittype().getName();
        response.subcategory = item.getSubcategory() == null ? null : item.getSubcategory().getName();
        response.category = item.getSubcategory() == null || item.getSubcategory().getCategory() == null
                ? null : item.getSubcategory().getCategory().getName();
        response.rop = item.getRop();
        response.locationId = location.getId();
        response.locationName = location.getName();
        response.locationType = location.getLocationtype() == null ? null : location.getLocationtype().getName();
        response.quantity = stock.getQuantity() == null ? BigDecimal.ZERO : stock.getQuantity();
        response.totalStock = totalStock == null ? BigDecimal.ZERO : totalStock;
        response.lastupdate = stock.getLastupdate();
        response.lowStock = item.getRop() != null
                && response.totalStock.compareTo(BigDecimal.valueOf(item.getRop())) <= 0;
        return response;
    }

    public static InventoryResponse zeroStock(Item item) {
        InventoryResponse response = new InventoryResponse();
        response.itemId = item.getId();
        response.itemnumber = item.getItemnumber();
        response.itemName = item.getName();
        response.brand = item.getItembrand() == null ? null : item.getItembrand().getName();
        response.unitType = item.getUnittype() == null ? null : item.getUnittype().getName();
        response.subcategory = item.getSubcategory() == null ? null : item.getSubcategory().getName();
        response.category = item.getSubcategory() == null || item.getSubcategory().getCategory() == null
                ? null : item.getSubcategory().getCategory().getName();
        response.rop = item.getRop();
        response.quantity = BigDecimal.ZERO;
        response.totalStock = BigDecimal.ZERO;
        response.lowStock = item.getRop() != null && item.getRop() >= 0;
        return response;
    }

    public Integer getId() { return id; }
    public Integer getItemId() { return itemId; }
    public String getItemnumber() { return itemnumber; }
    public String getItemName() { return itemName; }
    public String getBrand() { return brand; }
    public String getUnitType() { return unitType; }
    public String getCategory() { return category; }
    public String getSubcategory() { return subcategory; }
    public Integer getRop() { return rop; }
    public Integer getLocationId() { return locationId; }
    public String getLocationName() { return locationName; }
    public String getLocationType() { return locationType; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getTotalStock() { return totalStock; }
    public Timestamp getLastupdate() { return lastupdate; }
    public boolean isLowStock() { return lowStock; }
}
