package com.clippercuts.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Date;

public class ProductRequest {
    @NotBlank
    @Size(max = 45)
    private String itemnumber;

    @NotBlank
    @Size(max = 45)
    private String name;

    private Date dointroduced;

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal sprice;

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 5, fraction = 2)
    private BigDecimal pprice;

    @NotNull
    @Min(0)
    private Integer rop;

    @NotNull private Integer itemstatusId;
    @NotNull private Integer unittypeId;
    @NotNull private Integer itembrandId;
    @NotNull private Integer subcategoryId;

    public String getItemnumber() { return itemnumber; }
    public void setItemnumber(String itemnumber) { this.itemnumber = itemnumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Date getDointroduced() { return dointroduced; }
    public void setDointroduced(Date dointroduced) { this.dointroduced = dointroduced; }
    public BigDecimal getSprice() { return sprice; }
    public void setSprice(BigDecimal sprice) { this.sprice = sprice; }
    public BigDecimal getPprice() { return pprice; }
    public void setPprice(BigDecimal pprice) { this.pprice = pprice; }
    public Integer getRop() { return rop; }
    public void setRop(Integer rop) { this.rop = rop; }
    public Integer getItemstatusId() { return itemstatusId; }
    public void setItemstatusId(Integer itemstatusId) { this.itemstatusId = itemstatusId; }
    public Integer getUnittypeId() { return unittypeId; }
    public void setUnittypeId(Integer unittypeId) { this.unittypeId = unittypeId; }
    public Integer getItembrandId() { return itembrandId; }
    public void setItembrandId(Integer itembrandId) { this.itembrandId = itembrandId; }
    public Integer getSubcategoryId() { return subcategoryId; }
    public void setSubcategoryId(Integer subcategoryId) { this.subcategoryId = subcategoryId; }
}
