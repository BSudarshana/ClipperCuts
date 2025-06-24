package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Supplier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "registernumber")
    private String registernumber;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "contactnumber")
    private String contactnumber;
    @Basic
    @Column(name = "contactperson")
    private String contactperson;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "doregister")
    private Date doregister;
    @Basic
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Collection<Purchaseorder> purchaseorders;
    @ManyToOne
    @JoinColumn(name = "suplierstate_id", referencedColumnName = "id", nullable = false)
    private Supplierstate supplierstate;
    @ManyToOne
    @JoinColumn(name = "supplierstype_id", referencedColumnName = "id", nullable = false)
    private Supplierstype supplierstype;

    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    private Collection<Supply> supplies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisternumber() {
        return registernumber;
    }

    public void setRegisternumber(String registernumber) {
        this.registernumber = registernumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDoregister() {
        return doregister;
    }

    public void setDoregister(Date doregister) {
        this.doregister = doregister;
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
        Supplier supplier = (Supplier) o;
        return id == supplier.id && Objects.equals(name, supplier.name) && Objects.equals(registernumber, supplier.registernumber) && Objects.equals(address, supplier.address) && Objects.equals(contactnumber, supplier.contactnumber) && Objects.equals(contactperson, supplier.contactperson) && Objects.equals(email, supplier.email) && Objects.equals(doregister, supplier.doregister) && Objects.equals(description, supplier.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, registernumber, address, contactnumber, contactperson, email, doregister, description);
    }

    public Collection<Purchaseorder> getPurchaseorders() {
        return purchaseorders;
    }

    public void setPurchaseorders(Collection<Purchaseorder> purchaseorders) {
        this.purchaseorders = purchaseorders;
    }

    public Supplierstate getSupplierstate() {
        return supplierstate;
    }

    public void setSupplierstate(Supplierstate supplierstate) {
        this.supplierstate = supplierstate;
    }

    public Supplierstype getSupplierstype() {
        return supplierstype;
    }

    public void setSupplierstype(Supplierstype supplierstype) {
        this.supplierstype = supplierstype;
    }

    public Collection<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(Collection<Supply> supplies) {
        this.supplies = supplies;
    }
}
