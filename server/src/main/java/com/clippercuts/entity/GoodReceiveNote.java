package com.clippercuts.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;

@Entity
@Table(name="good_receive_note",schema="clippercuts")
public class GoodReceiveNote {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="grn_number",nullable=false,unique=true,length=45)
    private String grnNumber;

    @Column(nullable=false)
    private Date date;

    @Column(name="total_amount",precision=10,scale=2,nullable=false)
    private BigDecimal totalAmount;

    @Column(length=500)
    private String description;

    @ManyToOne(optional=false)
    @JoinColumn(name="grn_status_id")
    private GrnStatus grnStatus;

    @ManyToOne(optional=false)
    @JoinColumn(name="employee_id")
    private Employee employee;

    @ManyToOne(optional=false)
    @JoinColumn(name="purchaseorder_id")
    private Purchaseorder purchaseorder;

    @ManyToOne(optional=false,fetch=FetchType.LAZY)
    @JoinColumn(name="received_by_user_id")
    private User receivedByUser;

    @ManyToOne(optional=false)
    @JoinColumn(name="location_id")
    private Location location;

    @JsonIgnore
    @OneToMany(mappedBy="goodReceiveNote",cascade=CascadeType.ALL)
    private Collection<GrnItem> grnItems;

    public Integer getId(){return id;}
    public void setId(Integer id){this.id=id;}

    public String getGrnNumber(){return grnNumber;}
    public void setGrnNumber(String v){grnNumber=v;}

    public Date getDate(){return date;}
    public void setDate(Date v){date=v;}

    public BigDecimal getTotalAmount(){return totalAmount;}
    public void setTotalAmount(BigDecimal v){totalAmount=v;}

    public String getDescription(){return description;}
    public void setDescription(String v){description=v;}

    public GrnStatus getGrnStatus(){return grnStatus;}
    public void setGrnStatus(GrnStatus v){grnStatus=v;}

    public Employee getEmployee(){return employee;}
    public void setEmployee(Employee v){employee=v;}

    public Purchaseorder getPurchaseorder(){return purchaseorder;}
    public void setPurchaseorder(Purchaseorder v){purchaseorder=v;}

    public User getReceivedByUser(){return receivedByUser;}
    public void setReceivedByUser(User v){receivedByUser=v;}

    public Location getLocation(){return location;}
    public void setLocation(Location v){location=v;}

    public Collection<GrnItem> getGrnItems(){return grnItems;}
    public void setGrnItems(Collection<GrnItem> v){grnItems=v;}
}