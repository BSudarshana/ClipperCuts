package com.clippercuts.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

//    @Basic
//    @Column(name = "value")
//    private BigDecimal value;

//    @Basic
//    @Column(name = "maxvalue")
//    private BigDecimal maxvalue;

    @ManyToOne
    @JoinColumn(name = "discounttype_id", referencedColumnName = "id", nullable = false)
    private Discounttype discounttype;

    @ManyToOne
    @JoinColumn(name = "promotion_id", referencedColumnName = "id", nullable = false)
    private Promotion promotion;

    @Column(name = "discountvalue", precision = 10, scale = 2)
    private BigDecimal discountvalue;

    @Column(name = "maximumdiscount", precision = 10, scale = 2)
    private BigDecimal maximumdiscount;

    public BigDecimal getMaximumdiscount() {
        return maximumdiscount;
    }

    public void setMaximumdiscount(BigDecimal maximumdiscount) {
        this.maximumdiscount = maximumdiscount;
    }

    public BigDecimal getDiscountvalue() {
        return discountvalue;
    }

    public void setDiscountvalue(BigDecimal discountvalue) {
        this.discountvalue = discountvalue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public BigDecimal getValue() {
//        return value;
//    }
//
//    public void setValue(BigDecimal value) {
//        this.value = value;
//    }

//    public BigDecimal getMaxvalue() {
//        return maxvalue;
//    }
//
//    public void setMaxvalue(BigDecimal maxvalue) {
//        this.maxvalue = maxvalue;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(id, discount.id) && Objects.equals(discountvalue, discount.discountvalue) && Objects.equals(maximumdiscount, discount.maximumdiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discountvalue, maximumdiscount);
    }

    public Discounttype getDiscounttype() {
        return discounttype;
    }

    public void setDiscounttype(Discounttype discounttype) {
        this.discounttype = discounttype;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
}
