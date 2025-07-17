package com.clippercuts.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = " package_has_appointment", schema = "clippercuts", catalog = "")
public class PackageHasAppointment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", nullable = false)
    private Appointment appointment;
    @ManyToOne
    @JoinColumn(name = " Package_id", referencedColumnName = "id", nullable = false)
    private Package Package;

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
        PackageHasAppointment that = (PackageHasAppointment) o;
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

    public Package getPackage() {
        return Package;
    }

    public void setPackage(Package aPackage) {
        Package = aPackage;
    }
}
