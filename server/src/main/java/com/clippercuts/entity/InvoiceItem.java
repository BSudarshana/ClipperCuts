package com.clippercuts.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.math.BigDecimal;
@Entity @Table(name="invoice_item",schema="clippercuts")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal quantity;

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal price;

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal discount=BigDecimal.ZERO;

    @Column(nullable=false,precision=10,scale=2)
    private BigDecimal subtotal;

    @JsonIgnore @ManyToOne(optional=false) @JoinColumn(name="invoice_id")
    private Invoice invoice;

    @ManyToOne(optional=false) @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(optional=false) @JoinColumn(name="location_id")
    private Location location;

    public Integer getId(){return id;}

    public void setId(Integer v){id=v;}

    public BigDecimal getQuantity(){return quantity;}

    public void setQuantity(BigDecimal v){quantity=v;}

    public BigDecimal getPrice(){return price;}

    public void setPrice(BigDecimal v){price=v;} public BigDecimal getDiscount(){return discount;}

    public void setDiscount(BigDecimal v){discount=v;} public BigDecimal getSubtotal(){return subtotal;}

    public void setSubtotal(BigDecimal v){subtotal=v;} public Invoice getInvoice(){return invoice;}

    public void setInvoice(Invoice v){invoice=v;}

    public Item getItem(){return item;}

    public void setItem(Item v){item=v;}

    public Location getLocation(){return location;}

    public void setLocation(Location v){location=v;}

}
