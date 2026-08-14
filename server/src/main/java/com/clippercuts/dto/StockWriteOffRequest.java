package com.clippercuts.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class StockWriteOffRequest {

    @NotNull
    private Integer locationId;

    @NotBlank
    @Size(max = 50)
    private String reason;

    @Size(max = 255)
    private String note;

    @Valid
    @NotEmpty
    private List<StockWriteOffItemRequest> items;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<StockWriteOffItemRequest> getItems() {
        return items;
    }

    public void setItems(List<StockWriteOffItemRequest> items) {
        this.items = items;
    }
}
