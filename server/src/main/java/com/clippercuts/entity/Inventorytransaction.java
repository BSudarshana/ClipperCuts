package com.clippercuts.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "inventorytransaction", schema = "clippercuts")
public class Inventorytransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "transactiondate", nullable = false)
    private Instant transactiondate;

    @Size(max = 30)
    @NotNull
    @Column(name = "transactiontype", nullable = false, length = 30)
    private String transactiontype;

    @NotNull
    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "balanceafter", nullable = false, precision = 10, scale = 2)
    private BigDecimal balanceafter;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "performed_by_user_id", nullable = false)
    private User performedByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "good_receive_note_id")
    private GoodReceiveNote goodReceiveNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stocktransfer_id")
    private Stocktransfer stocktransfer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Instant transactiondate) {
        this.transactiondate = transactiondate;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBalanceafter() {
        return balanceafter;
    }

    public void setBalanceafter(BigDecimal balanceafter) {
        this.balanceafter = balanceafter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getPerformedByUser() {
        return performedByUser;
    }

    public void setPerformedByUser(User performedByUser) {
        this.performedByUser = performedByUser;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public GoodReceiveNote getGoodReceiveNote() {
        return goodReceiveNote;
    }

    public void setGoodReceiveNote(GoodReceiveNote goodReceiveNote) {
        this.goodReceiveNote = goodReceiveNote;
    }

    public Stocktransfer getStocktransfer() {
        return stocktransfer;
    }

    public void setStocktransfer(Stocktransfer stocktransfer) {
        this.stocktransfer = stocktransfer;
    }

}