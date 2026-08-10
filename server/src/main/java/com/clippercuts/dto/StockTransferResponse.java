package com.clippercuts.dto;

import com.clippercuts.entity.Stocktransfer;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StockTransferResponse {

    private Integer id;
    private Date transferdate;
    private String note;

    private Integer fromLocationId;
    private String fromLocationName;

    private Integer toLocationId;
    private String toLocationName;

    private Integer employeeId;
    private String employeeName;

    private String createdByUsername;

    private List<StockTransferItemResponse> items;

    public static StockTransferResponse from(Stocktransfer transfer) {

        StockTransferResponse response =
                new StockTransferResponse();

        response.id = transfer.getId();
        response.transferdate = transfer.getTransferdate();
        response.note = transfer.getNote();

        response.fromLocationId =
                transfer.getLocationfrom().getId();
        response.fromLocationName =
                transfer.getLocationfrom().getName();

        response.toLocationId =
                transfer.getLocationto().getId();
        response.toLocationName =
                transfer.getLocationto().getName();

        response.employeeId =
                transfer.getEmployee().getId();
        response.employeeName =
                transfer.getEmployee().getCallingname();

        if (transfer.getCreatedByUser() != null) {
            response.createdByUsername =
                    transfer.getCreatedByUser().getUsername();
        }

        response.items = transfer.getTransferitems()
                .stream()
                .map(StockTransferItemResponse::from)
                .collect(Collectors.toList());

        return response;
    }

    public Integer getId() {
        return id;
    }

    public Date getTransferdate() {
        return transferdate;
    }

    public String getNote() {
        return note;
    }

    public Integer getFromLocationId() {
        return fromLocationId;
    }

    public String getFromLocationName() {
        return fromLocationName;
    }

    public Integer getToLocationId() {
        return toLocationId;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public List<StockTransferItemResponse> getItems() {
        return items;
    }
}