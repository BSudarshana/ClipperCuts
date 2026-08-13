package com.clippercuts.util;

import com.clippercuts.dao.*;
import com.clippercuts.dto.*;
import com.clippercuts.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodReceiveNoteService {
    private final GoodReceiveNoteDao grnDao; private final GrnItemDao grnItemDao;
    private final PurchaseorderDao poDao; private final PostatusDao poStatusDao; private final GrnStatusDao grnStatusDao;
    private final InventoryLocationDao locationDao; private final ItemstockLocationDao stockDao;
    private final InventorytransactionDao transactionDao; private final UserDao userDao; private final NumberService numberService;
    public GoodReceiveNoteService(GoodReceiveNoteDao grnDao, GrnItemDao grnItemDao, PurchaseorderDao poDao,
                                  PostatusDao poStatusDao, GrnStatusDao grnStatusDao, InventoryLocationDao locationDao,
                                  ItemstockLocationDao stockDao, InventorytransactionDao transactionDao, UserDao userDao, NumberService numberService) {
        this.grnDao=grnDao; this.grnItemDao=grnItemDao; this.poDao=poDao; this.poStatusDao=poStatusDao;
        this.grnStatusDao=grnStatusDao; this.locationDao=locationDao; this.stockDao=stockDao;
        this.transactionDao=transactionDao; this.userDao=userDao; this.numberService=numberService;
    }

    @Transactional(readOnly=true)
    public List<GrnPurchaseOrderResponse> eligiblePurchaseOrders() {
        return poDao.findEligibleForGrn(Arrays.asList("active", "partially received")).stream()
                .map(this::toPoResponse).collect(Collectors.toList());
    }

    @Transactional
    public GoodReceiveNote receive(GrnCreateRequest request, String username) {
        User user=userDao.findByUsername(username);
        if(user==null) fail(HttpStatus.UNAUTHORIZED,"Logged-in user not found");
        Purchaseorder po=poDao.findForGrnUpdate(request.getPurchaseOrderId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Purchase order not found"));
        String currentStatus=po.getPostatus()==null ? "" : po.getPostatus().getName();
        if(!"Active".equalsIgnoreCase(currentStatus) && !"Partially Received".equalsIgnoreCase(currentStatus))
            fail(HttpStatus.CONFLICT,"Only Active or Partially Received purchase orders can be received");
        Location location=locationDao.findById(request.getLocationId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Inventory location not found"));
        if(user.getEmployee()==null) fail(HttpStatus.BAD_REQUEST,"Logged-in user is not linked to an employee");

        Map<Integer,Poitem> poLines=po.getPoitems().stream().collect(Collectors.toMap(Poitem::getId, x->x));
        Set<Integer> requestLineIds=new HashSet<>();
        BigDecimal total=BigDecimal.ZERO;
        List<ReceiptLine> validated=new ArrayList<>();
        for(GrnItemRequest line: request.getItems()) {
            if(!requestLineIds.add(line.getPoItemId())) fail(HttpStatus.BAD_REQUEST,"The same PO item cannot be received twice");
            Poitem poLine=poLines.get(line.getPoItemId());
            if(poLine==null) fail(HttpStatus.BAD_REQUEST,"A submitted item does not belong to the selected purchase order");
            BigDecimal qty=line.getReceivedQuantity();
            if(qty==null || qty.compareTo(BigDecimal.ZERO)<=0) fail(HttpStatus.BAD_REQUEST,"Received quantities must be greater than zero");
            BigDecimal already=received(po.getId(),poLine.getItem().getId());
            BigDecimal remaining=poLine.getQuantity().subtract(already);
            if(qty.compareTo(remaining)>0) fail(HttpStatus.CONFLICT,"Received quantity for " + poLine.getItem().getName() + " exceeds remaining quantity " + remaining);
            BigDecimal subtotal=qty.multiply(poLine.getUnitprice()).setScale(2,RoundingMode.HALF_UP);
            total=total.add(subtotal); validated.add(new ReceiptLine(poLine,qty,subtotal));
        }

        GoodReceiveNote grn=new GoodReceiveNote();
        grn.setGrnNumber(numberService.generateGrnNumber());

        grn.setDate(
                Date.valueOf(LocalDate.now())
        );

        grn.setTotalAmount(
                total.setScale(2,RoundingMode.HALF_UP)
        );

        grn.setDescription(
                request.getDescription()==null ? "" : request.getDescription().trim()
        );

        grn.setPurchaseorder(po); grn.setLocation(location);
        grn.setReceivedByUser(user);
        grn.setEmployee(user.getEmployee());

        // The configured GRN statuses are Draft, Completed, Cancelled and
        // Rejected. A posted receipt is therefore stored as Completed.
        grn.setGrnStatus(findGrnStatus("Completed"));
        grn=grnDao.save(grn);

        List<GrnItem> savedItems=new ArrayList<>();
        for(ReceiptLine value: validated) {
            GrnItem line=new GrnItem();
            line.setGoodReceiveNote(grn);
            line.setItem(value.poLine.getItem());
            line.setQuantity(value.quantity);
            line.setUnitcost(value.poLine.getUnitprice());
            line.setSubTotal(value.subtotal);
            savedItems.add(grnItemDao.save(line));

            ItemstockLocation stock=stockDao.findForUpdate(value.poLine.getItem().getId(),location.getId()).orElse(null);

            if(stock==null){
                stock=new ItemstockLocation();
                stock.setItem(value.poLine.getItem());
                stock.setLocation(location);
                stock.setQuantity(BigDecimal.ZERO);
            }

            BigDecimal balance=(stock.getQuantity()==null?BigDecimal.ZERO:stock.getQuantity()).add(value.quantity);

            stock.setQuantity(balance); stock.setLastupdate(new Timestamp(System.currentTimeMillis())); stockDao.save(stock);

            Inventorytransaction tx=new Inventorytransaction();
            tx.setTransactiondate(Instant.now());
            tx.setTransactiontype("GRN_IN");

            tx.setQuantity(value.quantity);
            tx.setBalanceafter(balance);
            tx.setItem(value.poLine.getItem());
            tx.setLocation(location);

            tx.setPerformedByUser(user); tx.setGoodReceiveNote(grn); tx.setDescription("Goods Receipt " + grn.getGrnNumber()); transactionDao.save(tx);
        }
        grn.setGrnItems(savedItems);
        po.setPostatus(findPoStatus(allItemsFullyReceived(po) ? "Fully Received" : "Partially Received")); poDao.save(po);
        return grn;
    }

    private GrnPurchaseOrderResponse toPoResponse(Purchaseorder po) {
        List<GrnPurchaseOrderItemResponse> items=po.getPoitems().stream().map(line->{
            BigDecimal already=received(po.getId(),line.getItem().getId());
            BigDecimal remaining=line.getQuantity().subtract(already).max(BigDecimal.ZERO);
            return new GrnPurchaseOrderItemResponse(line.getId(),line.getItem().getId(),line.getItem().getItemnumber(),
                    line.getItem().getName(),line.getItem().getUnittype()==null?"":line.getItem().getUnittype().getName(),
                    line.getQuantity(),already,remaining,line.getUnitprice());
        }).filter(x->x.getRemainingQuantity().compareTo(BigDecimal.ZERO)>0).collect(Collectors.toList());
        return new GrnPurchaseOrderResponse(po.getId(),po.getPoNumber(),po.getDate(),po.getPostatus().getName(),
                po.getSupplier().getId(),po.getSupplier().getName(),items);
    }
    private BigDecimal received(Integer poId,Integer itemId){ BigDecimal v=grnItemDao.totalReceived(poId,itemId); return v==null?BigDecimal.ZERO:v; }

    private boolean allItemsFullyReceived(Purchaseorder po){
        return po.getPoitems().stream()
                .allMatch(
                        line->received(po.getId(),line.getItem().getId()).compareTo(line.getQuantity())>=0
                );
    }

    private Postatus findPoStatus(String name){
        return poStatusDao.findByNameIgnoreCase(name)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"PO status not configured: "+name));
    }

    private GrnStatus findGrnStatus(String name){
        return grnStatusDao.findByNameIgnoreCase(name).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"GRN status not configured: "+name)
        );
    }

    private void fail(HttpStatus status,String message){
        throw new ResponseStatusException(status,message);
    }

    private static class ReceiptLine {
        final Poitem poLine;
        final BigDecimal quantity,subtotal;
        ReceiptLine(Poitem p,BigDecimal q,BigDecimal s){
            poLine=p;
            quantity=q;
            subtotal=s;
        }
    }
}