package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "good_receive_note", schema = "clippercuts", catalog = "")
public class GoodReceiveNote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "grn_number")
    private String grnNumber;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Basic
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "grn_status_id", referencedColumnName = "id", nullable = false)
    private GrnStatus grnStatus;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "purchaseorder_id", referencedColumnName = "id", nullable = false)
    private Purchaseorder purchaseorder;

    @JsonIgnore
    @OneToMany(mappedBy = "goodReceiveNote")
    private Collection<GrnItem> grnItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrnNumber() {
        return grnNumber;
    }

    public void setGrnNumber(String grnNumber) {
        this.grnNumber = grnNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodReceiveNote that = (GoodReceiveNote) o;
        return id == that.id && Objects.equals(grnNumber, that.grnNumber) && Objects.equals(date, that.date) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grnNumber, date, totalAmount, description);
    }

    public GrnStatus getGrnStatus() {
        return grnStatus;
    }

    public void setGrnStatus(GrnStatus grnStatus) {
        this.grnStatus = grnStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Purchaseorder getPurchaseorder() {
        return purchaseorder;
    }

    public void setPurchaseorder(Purchaseorder purchaseorder) {
        this.purchaseorder = purchaseorder;
    }

    public Collection<GrnItem> getGrnItems() {
        return grnItems;
    }

    public void setGrnItems(Collection<GrnItem> grnItems) {
        this.grnItems = grnItems;
    }
}
