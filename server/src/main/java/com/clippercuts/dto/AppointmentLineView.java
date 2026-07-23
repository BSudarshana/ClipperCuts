package com.clippercuts.dto;

import com.clippercuts.entity.Appointmentservice;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class AppointmentLineView {
    public Integer id;
    public Date appointmentDate;
    public Time startTime;
    public Time endTime;
    public Integer customerId;
    public String customerName;
    public Integer serviceId;
    public String serviceName;
    public BigDecimal agreedPrice;
    public Integer employeeId;
    public String employeeName;
    public Integer statusId;
    public String statusName;

    public static AppointmentLineView from(Appointmentservice line) {
        AppointmentLineView view = new AppointmentLineView();
        view.id = line.getId();
        view.appointmentDate = line.getAppointment().getAppointmentDate();
        view.startTime = line.getStartTime();
        view.endTime = line.getEndTime();
        view.customerId = line.getAppointment().getCustomer().getId();
        view.customerName = line.getAppointment().getCustomer().getCallingname();
        view.serviceId = line.getService().getId();
        view.serviceName = line.getService().getName();
        view.agreedPrice = line.getAgreedPrice();
        if (line.getEmployee() != null) {
            view.employeeId = line.getEmployee().getId();
            view.employeeName = line.getEmployee().getCallingname();
        }
        view.statusId = line.getAppointmentservicestatus().getId();
        view.statusName = line.getAppointmentservicestatus().getName();
        return view;
    }
}
