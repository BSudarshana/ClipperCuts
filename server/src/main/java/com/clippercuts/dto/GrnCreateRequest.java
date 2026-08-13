package com.clippercuts.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class GrnCreateRequest {
    @NotNull private Integer purchaseOrderId;
    @NotNull private Integer locationId;
    @Size(max = 500) private String description;
    @Valid @NotEmpty private List<GrnItemRequest> items;

    public Integer getPurchaseOrderId() { return purchaseOrderId; }
    public void setPurchaseOrderId(Integer purchaseOrderId) { this.purchaseOrderId = purchaseOrderId; }
    public Integer getLocationId() { return locationId; }
    public void setLocationId(Integer locationId) { this.locationId = locationId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<GrnItemRequest> getItems() { return items; }
    public void setItems(List<GrnItemRequest> items) { this.items = items; }
}
