package com.clippercuts.dto;

import com.clippercuts.entity.Stockwriteoff;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StockWriteOffResponse {

    private Integer id;
    private String writeoffnumber;
    private Timestamp writeoffdate;
    private String reason;
    private String note;
    private Integer locationId;
    private String locationName;
    private String createdByUsername;
    private BigDecimal totalQuantity;
    private List<StockWriteOffItemResponse> items;

    public static StockWriteOffResponse from(Stockwriteoff writeOff) {
        StockWriteOffResponse response = new StockWriteOffResponse();
        response.id = writeOff.getId();
        response.writeoffnumber = writeOff.getWriteoffnumber();
        response.writeoffdate = writeOff.getWriteoffdate();
        response.reason = writeOff.getReason();
        response.note = writeOff.getNote();
        response.locationId = writeOff.getLocation().getId();
        response.locationName = writeOff.getLocation().getName();
        response.createdByUsername = writeOff.getCreatedByUser().getUsername();

        response.items = writeOff.getItems() == null
                ? Collections.emptyList()
                : writeOff.getItems()
                .stream()
                .map(StockWriteOffItemResponse::from)
                .collect(Collectors.toList());

        response.totalQuantity = response.items
                .stream()
                .map(StockWriteOffItemResponse::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return response;
    }

    public Integer getId() {
        return id;
    }

    public String getWriteoffnumber() {
        return writeoffnumber;
    }

    public Timestamp getWriteoffdate() {
        return writeoffdate;
    }

    public String getReason() {
        return reason;
    }

    public String getNote() {
        return note;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public List<StockWriteOffItemResponse> getItems() {
        return items;
    }
}
