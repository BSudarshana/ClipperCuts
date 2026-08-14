package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stockwriteoff", schema = "clippercuts")
public class Stockwriteoff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String writeoffnumber;

    @NotNull
    @Column(nullable = false)
    private Timestamp writeoffdate;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String reason;

    @Size(max = 255)
    @Column(length = 255)
    private String note;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdByUser;

    @JsonIgnore
    @OneToMany(mappedBy = "stockwriteoff", fetch = FetchType.LAZY)
    private List<Stockwriteoffitem> items = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWriteoffnumber() {
        return writeoffnumber;
    }

    public void setWriteoffnumber(String writeoffnumber) {
        this.writeoffnumber = writeoffnumber;
    }

    public Timestamp getWriteoffdate() {
        return writeoffdate;
    }

    public void setWriteoffdate(Timestamp writeoffdate) {
        this.writeoffdate = writeoffdate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public List<Stockwriteoffitem> getItems() {
        return items;
    }

    public void setItems(List<Stockwriteoffitem> items) {
        this.items = items;
    }
}
