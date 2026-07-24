package com.clippercuts.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = " package_has_invoice", schema = "clippercuts", catalog = "")
public class PackageHasInvoice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "price")
    private String price;
    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id", nullable = false)
    private Invoice invoice;
    @ManyToOne
    @JoinColumn(name = "Package_id", referencedColumnName = "id", nullable = false)
    private Package Package;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String prpice) {
        this.price = prpice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PackageHasInvoice that = (PackageHasInvoice) o;
        return Objects.equals(id, that.id) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Package getPackage() {
        return Package;
    }

    public void setPackage(Package aPackage) {
        Package = aPackage;
    }
}
