package com.clippercuts.dto;

import com.clippercuts.entity.GoodReceiveNote;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class GrnResponse {
    private final Integer id; private final String grnNumber; private final Date date;
    private final BigDecimal totalAmount; private final String description; private final String status;
    private final Integer purchaseOrderId; private final String poNumber; private final String supplierName;
    private final Integer locationId; private final String locationName; private final String receivedByUsername;
    private final String employeeName; private final List<GrnItemResponse> items;
    public GrnResponse(GoodReceiveNote grn) {
        id=grn.getId(); grnNumber=grn.getGrnNumber(); date=grn.getDate(); totalAmount=grn.getTotalAmount();
        description=grn.getDescription(); status=grn.getGrnStatus().getName();
        purchaseOrderId=grn.getPurchaseorder().getId(); poNumber=grn.getPurchaseorder().getPoNumber();
        supplierName=grn.getPurchaseorder().getSupplier().getName(); locationId=grn.getLocation().getId();
        locationName=grn.getLocation().getName(); receivedByUsername=grn.getReceivedByUser().getUsername();
        employeeName=grn.getEmployee().getFullname();
        items=grn.getGrnItems().stream().map(GrnItemResponse::new).collect(Collectors.toList());
    }
    public Integer getId(){return id;} public String getGrnNumber(){return grnNumber;} public Date getDate(){return date;}
    public BigDecimal getTotalAmount(){return totalAmount;} public String getDescription(){return description;}
    public String getStatus(){return status;} public Integer getPurchaseOrderId(){return purchaseOrderId;}
    public String getPoNumber(){return poNumber;} public String getSupplierName(){return supplierName;}
    public Integer getLocationId(){return locationId;} public String getLocationName(){return locationName;}
    public String getReceivedByUsername(){return receivedByUsername;} public String getEmployeeName(){return employeeName;}
    public List<GrnItemResponse> getItems(){return items;}
}
