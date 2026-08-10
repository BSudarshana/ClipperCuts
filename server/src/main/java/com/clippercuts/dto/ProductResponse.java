package com.clippercuts.dto;

import com.clippercuts.entity.*;
import java.math.BigDecimal;
import java.sql.Date;

public class ProductResponse {
    private Integer id;
    private String itemnumber;
    private String name;
    private Date dointroduced;
    private BigDecimal sprice;
    private BigDecimal pprice;
    private Integer rop;
    private BigDecimal totalStock;
    private Itemstatus itemstatus;
    private Unittype unittype;
    private Itembrand itembrand;
    private Subcategory subcategory;

    public static ProductResponse fromEntity(Item item, BigDecimal totalStock) {
        ProductResponse r = new ProductResponse();
        r.id = item.getId(); r.itemnumber = item.getItemnumber(); r.name = item.getName();
        r.dointroduced = item.getDointroduced(); r.sprice = item.getSprice();
        r.pprice = item.getPprice(); r.rop = item.getRop();
        r.totalStock = totalStock == null ? BigDecimal.ZERO : totalStock;
        r.itemstatus = item.getItemstatus(); r.unittype = item.getUnittype();
        r.itembrand = item.getItembrand(); r.subcategory = item.getSubcategory();
        return r;
    }

    public Integer getId() { return id; }
    public String getItemnumber() { return itemnumber; }
    public String getName() { return name; }
    public Date getDointroduced() { return dointroduced; }
    public BigDecimal getSprice() { return sprice; }
    public BigDecimal getPprice() { return pprice; }
    public Integer getRop() { return rop; }
    public BigDecimal getTotalStock() { return totalStock; }
    public Itemstatus getItemstatus() { return itemstatus; }
    public Unittype getUnittype() { return unittype; }
    public Itembrand getItembrand() { return itembrand; }
    public Subcategory getSubcategory() { return subcategory; }
}
