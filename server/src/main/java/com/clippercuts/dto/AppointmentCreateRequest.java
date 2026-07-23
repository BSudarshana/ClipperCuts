package com.clippercuts.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class AppointmentCreateRequest {
    @NotNull private Date appointmentDate;
    @NotNull private Time appointmentTime;
    private String description;
    @NotNull private Integer customerId;
    @NotEmpty @Valid private List<AppointmentLineRequest> services;

    public Date getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(Date appointmentDate) { this.appointmentDate = appointmentDate; }
    public Time getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(Time appointmentTime) { this.appointmentTime = appointmentTime; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    public List<AppointmentLineRequest> getServices() { return services; }
    public void setServices(List<AppointmentLineRequest> services) { this.services = services; }
}
