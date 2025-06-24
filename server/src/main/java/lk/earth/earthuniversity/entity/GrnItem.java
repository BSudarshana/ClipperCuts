package lk.earth.earthuniversity.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "grn_item", schema = "clippercuts", catalog = "")
public class GrnItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
    @Basic
    @Column(name = "unitcost")
    private BigDecimal unitcost;
    @Basic
    @Column(name = "sub_total")
    private BigDecimal subTotal;
    @ManyToOne
    @JoinColumn(name = "good_receive_note_id", referencedColumnName = "id", nullable = false)
    private GoodReceiveNote goodReceiveNote;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

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

    public BigDecimal getUnitcost() {
        return unitcost;
    }

    public void setUnitcost(BigDecimal unitcost) {
        this.unitcost = unitcost;
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
        GrnItem grnItem = (GrnItem) o;
        return id == grnItem.id && Objects.equals(quantity, grnItem.quantity) && Objects.equals(unitcost, grnItem.unitcost) && Objects.equals(subTotal, grnItem.subTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, unitcost, subTotal);
    }

    public GoodReceiveNote getGoodReceiveNote() {
        return goodReceiveNote;
    }

    public void setGoodReceiveNote(GoodReceiveNote goodReceiveNote) {
        this.goodReceiveNote = goodReceiveNote;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
