package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Poitem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @Basic
    @Column(name = "sub_total")
    private BigDecimal subTotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;
    @ManyToOne
    @JoinColumn(name = "purchaseorder_id", referencedColumnName = "id", nullable = false)
    private Purchaseorder purchaseorder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Poitem poitem = (Poitem) o;
        return id == poitem.id && Objects.equals(quantity, poitem.quantity) && Objects.equals(subTotal, poitem.subTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, subTotal);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Purchaseorder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(Purchaseorder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }
}
