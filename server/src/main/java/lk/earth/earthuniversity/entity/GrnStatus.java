package lk.earth.earthuniversity.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "grn_status", schema = "clippercuts", catalog = "")
public class GrnStatus {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "grnStatus")
    private Collection<GoodReceiveNote> goodReceiveNotes;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrnStatus grnStatus = (GrnStatus) o;
        return id == grnStatus.id && Objects.equals(name, grnStatus.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public Collection<GoodReceiveNote> getGoodReceiveNotes() {
        return goodReceiveNotes;
    }

    public void setGoodReceiveNotes(Collection<GoodReceiveNote> goodReceiveNotes) {
        this.goodReceiveNotes = goodReceiveNotes;
    }
}
