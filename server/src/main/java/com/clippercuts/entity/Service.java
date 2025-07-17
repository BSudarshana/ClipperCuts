package com.clippercuts.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Service {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "duration")
    private Integer duration;
    @Basic
    @Column(name = "price")
    private BigDecimal price;
    @OneToMany(mappedBy = "service")
    private Collection<PackageHasService> packageHasServices;
    @OneToMany(mappedBy = "service")
    private Collection<Appointmentservice> appointmentservices;
    @ManyToOne
    @JoinColumn(name = "servicestatus_id", referencedColumnName = "id", nullable = false)
    private Servicestatus servicestatus;
    @ManyToOne
    @JoinColumn(name = "servicecategory_id", referencedColumnName = "id", nullable = false)
    private Servicecategory servicecategory;
    @OneToMany(mappedBy = "service")
    private Collection<ServiceHasEmployee> serviceHasEmployees;
    @OneToMany(mappedBy = "service")
    private Collection<ServiceHasPromotion> serviceHasPromotions;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id) && Objects.equals(name, service.name) && Objects.equals(duration, service.duration) && Objects.equals(price, service.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, price);
    }

    public Collection<PackageHasService> getPackageHasServices() {
        return packageHasServices;
    }

    public void setPackageHasServices(Collection<PackageHasService> packageHasServices) {
        this.packageHasServices = packageHasServices;
    }

    public Collection<Appointmentservice> getAppointmentservices() {
        return appointmentservices;
    }

    public void setAppointmentservices(Collection<Appointmentservice> appointmentservices) {
        this.appointmentservices = appointmentservices;
    }

    public Servicestatus getServicestatus() {
        return servicestatus;
    }

    public void setServicestatus(Servicestatus servicestatus) {
        this.servicestatus = servicestatus;
    }

    public Servicecategory getServicecategory() {
        return servicecategory;
    }

    public void setServicecategory(Servicecategory servicecategory) {
        this.servicecategory = servicecategory;
    }

    public Collection<ServiceHasEmployee> getServiceHasEmployees() {
        return serviceHasEmployees;
    }

    public void setServiceHasEmployees(Collection<ServiceHasEmployee> serviceHasEmployees) {
        this.serviceHasEmployees = serviceHasEmployees;
    }

    public Collection<ServiceHasPromotion> getServiceHasPromotions() {
        return serviceHasPromotions;
    }

    public void setServiceHasPromotions(Collection<ServiceHasPromotion> serviceHasPromotions) {
        this.serviceHasPromotions = serviceHasPromotions;
    }
}
