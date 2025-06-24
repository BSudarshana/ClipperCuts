package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Purchaseorder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "po_number")
    private String poNumber;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Basic
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "purchaseorder")
    private Collection<GoodReceiveNote> goodReceiveNotes;

    @JsonIgnore
    @OneToMany(mappedBy = "purchaseorder")
    private Collection<Poitem> poitems;
    @ManyToOne
    @JoinColumn(name = "postatus_id", referencedColumnName = "id", nullable = false)
    private Postatus postatus;
    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id", nullable = false)
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
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
        Purchaseorder that = (Purchaseorder) o;
        return id == that.id && Objects.equals(poNumber, that.poNumber) && Objects.equals(date, that.date) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, poNumber, date, totalAmount, description);
    }

    public Collection<GoodReceiveNote> getGoodReceiveNotes() {
        return goodReceiveNotes;
    }

    public void setGoodReceiveNotes(Collection<GoodReceiveNote> goodReceiveNotes) {
        this.goodReceiveNotes = goodReceiveNotes;
    }

    public Collection<Poitem> getPoitems() {
        return poitems;
    }

    public void setPoitems(Collection<Poitem> poitems) {
        this.poitems = poitems;
    }

    public Postatus getPostatus() {
        return postatus;
    }

    public void setPostatus(Postatus postatus) {
        this.postatus = postatus;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
