package com.clippercuts.dto;

import javax.validation.constraints.NotNull;

public class AppointmentLineRequest {
    @NotNull private Integer serviceId;
    private Integer employeeId;

    public Integer getServiceId() { return serviceId; }
    public void setServiceId(Integer serviceId) { this.serviceId = serviceId; }
    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
}
