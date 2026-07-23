package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Objects;

@Entity
public class Appointmentservice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointment appointment;


    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private Service service;


    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Time startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Time endTime;

    @NotNull
    @Column(name = "agreed_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal agreedPrice;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "appointmentservicestatus_id", nullable = false)
    private Appointmentservicestatus appointmentservicestatus;

    public Appointmentservicestatus getAppointmentservicestatus() {
        return appointmentservicestatus;
    }

    public void setAppointmentservicestatus(Appointmentservicestatus appointmentservicestatus) {
        this.appointmentservicestatus = appointmentservicestatus;
    }

    public BigDecimal getAgreedPrice() {
        return agreedPrice;
    }

    public void setAgreedPrice(BigDecimal agreedPrice) {
        this.agreedPrice = agreedPrice;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointmentservice that = (Appointmentservice) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
