package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Item {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "itemnumber")
    private String itemnumber;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "dointroduced")
    private Date dointroduced;
    @Basic
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Basic
    @Column(name = "sprice")
    private BigDecimal sprice;
    @Basic
    @Column(name = "pprice")
    private BigDecimal pprice;
    @Basic
    @Column(name = "rop")
    private Integer rop;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Collection<GrnItem> grnItems;
    @ManyToOne
    @JoinColumn(name = "itemstatus_id", referencedColumnName = "id", nullable = false)
    private Itemstatus itemstatus;
    @ManyToOne
    @JoinColumn(name = "unittype_id", referencedColumnName = "id", nullable = false)
    private Unittype unittype;
    @ManyToOne
    @JoinColumn(name = "itembrand_id", referencedColumnName = "id", nullable = false)
    private Itembrand itembrand;
    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id", nullable = false)
    private Subcategory subcategory;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private Collection<Poitem> poitems;
    @OneToMany(mappedBy = "item")
    private Collection<InvoiceItem> invoiceItems;
    @OneToMany(mappedBy = "item")
    private Collection<ItemstockLocation> itemstockLocations;
    @OneToMany(mappedBy = "item")
    private Collection<Transferitem> transferitems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemnumber() {
        return itemnumber;
    }

    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDointroduced() {
        return dointroduced;
    }

    public void setDointroduced(Date dointroduced) {
        this.dointroduced = dointroduced;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSprice() {
        return sprice;
    }

    public void setSprice(BigDecimal sprice) {
        this.sprice = sprice;
    }

    public BigDecimal getPprice() {
        return pprice;
    }

    public void setPprice(BigDecimal pprice) {
        this.pprice = pprice;
    }

    public Integer getRop() {
        return rop;
    }

    public void setRop(Integer rop) {
        this.rop = rop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && Objects.equals(itemnumber, item.itemnumber) && Objects.equals(name, item.name) && Objects.equals(dointroduced, item.dointroduced) && Objects.equals(quantity, item.quantity) && Objects.equals(sprice, item.sprice) && Objects.equals(pprice, item.pprice) && Objects.equals(rop, item.rop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemnumber, name, dointroduced, quantity, sprice, pprice, rop);
    }

    public Collection<GrnItem> getGrnItems() {
        return grnItems;
    }

    public void setGrnItems(Collection<GrnItem> grnItems) {
        this.grnItems = grnItems;
    }

    public Itemstatus getItemstatus() {
        return itemstatus;
    }

    public void setItemstatus(Itemstatus itemstatus) {
        this.itemstatus = itemstatus;
    }

    public Unittype getUnittype() {
        return unittype;
    }

    public void setUnittype(Unittype unittype) {
        this.unittype = unittype;
    }

    public Itembrand getItembrand() {
        return itembrand;
    }

    public void setItembrand(Itembrand itembrand) {
        this.itembrand = itembrand;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public Collection<Poitem> getPoitems() {
        return poitems;
    }

    public void setPoitems(Collection<Poitem> poitems) {
        this.poitems = poitems;
    }

    public Collection<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Collection<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Collection<ItemstockLocation> getItemstockLocations() {
        return itemstockLocations;
    }

    public void setItemstockLocations(Collection<ItemstockLocation> itemstockLocations) {
        this.itemstockLocations = itemstockLocations;
    }

    public Collection<Transferitem> getTransferitems() {
        return transferitems;
    }

    public void setTransferitems(Collection<Transferitem> transferitems) {
        this.transferitems = transferitems;
    }
}
