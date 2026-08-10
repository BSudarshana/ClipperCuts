package com.clippercuts.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class StockTransferRequest {

    @NotNull
    private Integer fromLocationId;

    @NotNull
    private Integer toLocationId;

    @NotNull
    private Integer employeeId;

    @Size(max = 200)
    private String note;

    @Valid
    @NotEmpty
    private List<StockTransferItemRequest> items;

    public Integer getFromLocationId() {
        return fromLocationId;
    }

    public void setFromLocationId(Integer fromLocationId) {
        this.fromLocationId = fromLocationId;
    }

    public Integer getToLocationId() {
        return toLocationId;
    }

    public void setToLocationId(Integer toLocationId) {
        this.toLocationId = toLocationId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<StockTransferItemRequest> getItems() {
        return items;
    }

    public void setItems(List<StockTransferItemRequest> items) {
        this.items = items;
    }
}