package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity @Table(name="grn_item",schema="clippercuts")
public class GrnItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal quantity;

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal unitcost;

    @Column(name="sub_total",nullable=false,precision=10,scale=2)
    private BigDecimal subTotal;

    @JsonIgnore
    @ManyToOne(optional=false) @JoinColumn(name="good_receive_note_id")
    private GoodReceiveNote goodReceiveNote;

    @ManyToOne(optional=false) @JoinColumn(name="item_id") private Item item;

    public Integer getId(){return id;}
    public void setId(Integer id){this.id=id;}

    public BigDecimal getQuantity(){return quantity;}
    public void setQuantity(BigDecimal v){quantity=v;}

    public BigDecimal getUnitcost(){return unitcost;}
    public void setUnitcost(BigDecimal v){unitcost=v;}

    public BigDecimal getSubTotal(){return subTotal;}
    public void setSubTotal(BigDecimal v){subTotal=v;}

    public GoodReceiveNote getGoodReceiveNote(){return goodReceiveNote;}
    public void setGoodReceiveNote(GoodReceiveNote v){goodReceiveNote=v;}

    public Item getItem(){return item;}
    public void setItem(Item v){item=v;}
}