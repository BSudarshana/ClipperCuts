package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Location {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "location")
    private Collection<InvoiceItem> invoiceItems;
    @OneToMany(mappedBy = "location")
    private Collection<ItemstockLocation> itemstockLocations;
    @ManyToOne
    @JoinColumn(name = "locationtype_id", referencedColumnName = "id", nullable = false)
    private Locationtype locationtype;
    @OneToMany(mappedBy = "locationfrom")
    private Collection<Stocktransfer> stocktransfersFrom;
    @OneToMany(mappedBy = "locationto")
    private Collection<Stocktransfer> stocktransfersTo;

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
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(name, location.name) && Objects.equals(description, location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
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

    public Locationtype getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(Locationtype locationtype) {
        this.locationtype = locationtype;
    }

    public Collection<Stocktransfer> getStocktransfersFrom() {
        return stocktransfersFrom;
    }

    public void setStocktransfersFrom(Collection<Stocktransfer> stocktransfersFrom) {
        this.stocktransfersFrom = stocktransfersFrom;
    }

    public Collection<Stocktransfer> getStocktransfersTo() {
        return stocktransfersTo;
    }

    public void setStocktransfersTo(Collection<Stocktransfer> stocktransfersTo) {
        this.stocktransfersTo = stocktransfersTo;
    }
}
