package com.clippercuts.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Stocktransfer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "transferdate")
    private Date transferdate;
    @Basic
    @Column(name = "note")
    private String note;
    @ManyToOne
    @JoinColumn(name = "from_location_id", referencedColumnName = "id", nullable = false)
    private Location locationfrom;
    @ManyToOne
    @JoinColumn(name = "to_location_id", referencedColumnName = "id", nullable = false)
    private Location locationto;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @OneToMany(mappedBy = "stocktransfer")
    private Collection<Transferitem> transferitems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTransferdate() {
        return transferdate;
    }

    public void setTransferdate(Date transferdate) {
        this.transferdate = transferdate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stocktransfer that = (Stocktransfer) o;
        return Objects.equals(id, that.id) && Objects.equals(transferdate, that.transferdate) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transferdate, note);
    }

    public Location getLocationfrom() {
        return locationfrom;
    }

    public void setLocationfrom(Location locationfrom) {
        this.locationfrom = locationfrom;
    }

    public Location getLocationto() {
        return locationto;
    }

    public void setLocationto(Location locationto) {
        this.locationto = locationto;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Collection<Transferitem> getTransferitems() {
        return transferitems;
    }

    public void setTransferitems(Collection<Transferitem> transferitems) {
        this.transferitems = transferitems;
    }
}
