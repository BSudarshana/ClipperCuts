package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Promotion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "startdate")
    private Date startdate;

    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private Collection<Discount> discounts;

    @JsonIgnore
    @OneToMany(mappedBy = "promotion")
    private Collection<Invoice> invoices;
    @ManyToOne
    @JoinColumn(name = "promotionstatus_id", referencedColumnName = "id", nullable = false)
    private Promotionstatus promotionstatus;
    @OneToMany(mappedBy = "promotion")
    private Collection<ServiceHasPromotion> serviceHasPromotions;
    @Basic
    @Column(name = "enddate")
    private Date enddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotion promotion = (Promotion) o;
        return Objects.equals(id, promotion.id) && Objects.equals(title, promotion.title) && Objects.equals(description, promotion.description) && Objects.equals(startdate, promotion.startdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startdate);
    }

    public Collection<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Collection<Discount> discounts) {
        this.discounts = discounts;
    }

    public Collection<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Collection<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Promotionstatus getPromotionstatus() {
        return promotionstatus;
    }

    public void setPromotionstatus(Promotionstatus promotionstatus) {
        this.promotionstatus = promotionstatus;
    }

    public Collection<ServiceHasPromotion> getServiceHasPromotions() {
        return serviceHasPromotions;
    }

    public void setServiceHasPromotions(Collection<ServiceHasPromotion> serviceHasPromotions) {
        this.serviceHasPromotions = serviceHasPromotions;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
