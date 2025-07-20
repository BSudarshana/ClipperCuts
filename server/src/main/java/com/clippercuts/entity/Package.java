package com.clippercuts.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = " package", schema = "clippercuts", catalog = "")
public class Package {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @Basic
    @Column(name = "duration")
    private Integer duration;
    @Basic
    @Column(name = "status")
    private Byte status;
    @OneToMany(mappedBy = "Package")
    private Collection<PackageHasAppointment> packageHasAppointments;
    @OneToMany(mappedBy = "Package")
    private Collection<PackageHasInvoice> packageHasInvoices;
    @OneToMany(mappedBy = "Package")
    private Collection<PackageHasService> packageHasServices;
    @Basic
    @Column(name = "packagnumber")
    private String packagnumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(id, aPackage.id) && Objects.equals(name, aPackage.name) && Objects.equals(price, aPackage.price) && Objects.equals(duration, aPackage.duration) && Objects.equals(status, aPackage.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, duration, status);
    }

    public Collection<PackageHasAppointment> getPackageHasAppointments() {
        return packageHasAppointments;
    }

    public void setPackageHasAppointments(Collection<PackageHasAppointment> packageHasAppointments) {
        this.packageHasAppointments = packageHasAppointments;
    }

    public Collection<PackageHasInvoice> getPackageHasInvoices() {
        return packageHasInvoices;
    }

    public void setPackageHasInvoices(Collection<PackageHasInvoice> packageHasInvoices) {
        this.packageHasInvoices = packageHasInvoices;
    }

    public Collection<PackageHasService> getPackageHasServices() {
        return packageHasServices;
    }

    public void setPackageHasServices(Collection<PackageHasService> packageHasServices) {
        this.packageHasServices = packageHasServices;
    }

    public String getPackagnumber() {
        return packagnumber;
    }

    public void setPackagnumber(String packagnumber) {
        this.packagnumber = packagnumber;
    }
}
