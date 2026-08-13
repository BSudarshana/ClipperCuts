package com.clippercuts.dto;

import java.sql.Date;
import java.util.List;

public class GrnPurchaseOrderResponse {
    private final Integer id; private final String poNumber; private final Date date;
    private final String status; private final Integer supplierId; private final String supplierName;
    private final List<GrnPurchaseOrderItemResponse> items;
    public GrnPurchaseOrderResponse(Integer id, String poNumber, Date date, String status,
                                    Integer supplierId, String supplierName,
                                    List<GrnPurchaseOrderItemResponse> items) {
        this.id=id; this.poNumber=poNumber; this.date=date; this.status=status;
        this.supplierId=supplierId; this.supplierName=supplierName; this.items=items;
    }
    public Integer getId(){return id;} public String getPoNumber(){return poNumber;}
    public Date getDate(){return date;} public String getStatus(){return status;}
    public Integer getSupplierId(){return supplierId;} public String getSupplierName(){return supplierName;}
    public List<GrnPurchaseOrderItemResponse> getItems(){return items;}
}
