package com.clippercuts.controller;

import com.clippercuts.dao.PurchaseorderDao;
import com.clippercuts.dao.UserDao;
import com.clippercuts.entity.Poitem;
import com.clippercuts.entity.Purchaseorder;
import com.clippercuts.entity.User;
import com.clippercuts.util.NumberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/purchaseorders")
public class PurchaseorderController {

    private final PurchaseorderDao purchaseorderDao;
    private final UserDao userDao;
    private final NumberService numberService;

    public PurchaseorderController(PurchaseorderDao purchaseorderDao, UserDao userDao,
                                   NumberService numberService) {
        this.purchaseorderDao = purchaseorderDao;
        this.userDao = userDao;
        this.numberService = numberService;
    }

    @GetMapping(produces = "application/json")
    public List<Purchaseorder> get(@RequestParam HashMap<String, String> params) {
        Stream<Purchaseorder> stream = purchaseorderDao.findAll().stream();
        String poNumber = params.get("po_number");
        String postatusId = params.get("postatusid");

        if (poNumber != null && !poNumber.trim().isEmpty()) {
            String value = poNumber.trim().toLowerCase();
            stream = stream.filter(po -> po.getPoNumber() != null &&
                    po.getPoNumber().toLowerCase().contains(value));
        }
        if (postatusId != null && !postatusId.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(postatusId);
                stream = stream.filter(po -> po.getPostatus() != null && po.getPostatus().getId() == id);
            } catch (NumberFormatException ignored) {
                return java.util.Collections.emptyList();
            }
        }
        return stream.collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Purchaseorder getById(@PathVariable Integer id) {
        return purchaseorderDao.findPOById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Purchaseorder purchaseorder,
                                       Authentication authentication) {
        purchaseorder.setPoNumber(numberService.generatePurchaseOrderNumber());
        String errors = validate(purchaseorder, null);
        User loggedUser = findLoggedUser(authentication);
        if (loggedUser == null) errors += "<br> Logged-in user was not found";

        if (errors.isEmpty()) {
            prepareItemsAndTotal(purchaseorder);
            purchaseorder.setCreatedByUser(loggedUser);
            purchaseorderDao.save(purchaseorder);
        }
        return response(purchaseorder.getId(), purchaseorder.getPoNumber(), errors);
    }

    @PutMapping
    public HashMap<String, String> update(@RequestBody Purchaseorder purchaseorder,
                                          Authentication authentication) {
        Purchaseorder existing = purchaseorderDao.findPOById(purchaseorder.getId());
        if (existing != null) purchaseorder.setPoNumber(existing.getPoNumber());
        String errors = existing == null ? "<br> Purchase order does not exist" :
                validate(purchaseorder, purchaseorder.getId());
        User loggedUser = findLoggedUser(authentication);
        if (loggedUser == null) errors += "<br> Logged-in user was not found";

        if (errors.isEmpty()) {
            prepareItemsAndTotal(purchaseorder);
            purchaseorder.setCreatedByUser(existing.getCreatedByUser() != null
                    ? existing.getCreatedByUser() : loggedUser);
            purchaseorderDao.save(purchaseorder);
        }
        return response(purchaseorder.getId(), purchaseorder.getPoNumber(), errors);
    }

    @DeleteMapping("/{id}")
    public HashMap<String, String> delete(@PathVariable Integer id) {
        Purchaseorder purchaseorder = purchaseorderDao.findPOById(id);
        String errors = purchaseorder == null ? "<br> Purchase order does not exist" : "";
        if (errors.isEmpty()) purchaseorderDao.delete(purchaseorder);
        return response(id, purchaseorder == null ? null : purchaseorder.getPoNumber(), errors);
    }

    private String validate(Purchaseorder purchaseorder, Integer currentId) {
        String errors = "";
        if (purchaseorder.getPoNumber() == null || purchaseorder.getPoNumber().trim().isEmpty())
            errors += "<br> PO number is required";
        else if (purchaseorder.getPoNumber().trim().length() > 45)
            errors += "<br> PO number cannot exceed 45 characters";
        else {
            Purchaseorder duplicate = purchaseorderDao.findByPONumber(purchaseorder.getPoNumber());
            if (duplicate != null && (currentId == null || duplicate.getId() != currentId))
                errors += "<br> Existing PO number";
        }
        if (purchaseorder.getDate() == null) errors += "<br> Date is required";
        if (purchaseorder.getPostatus() == null || purchaseorder.getPostatus().getId() == 0)
            errors += "<br> Status is required";
        if (purchaseorder.getSupplier() == null || purchaseorder.getSupplier().getId() == 0)
            errors += "<br> Supplier is required";
        if (purchaseorder.getEmployee() == null || purchaseorder.getEmployee().getId() == 0)
            errors += "<br> Employee is required";
        if (purchaseorder.getPoitems() == null || purchaseorder.getPoitems().isEmpty())
            errors += "<br> At least one PO item is required";
        else {
            for (Poitem line : purchaseorder.getPoitems()) {
                if (line.getItem() == null || line.getItem().getId() == 0)
                    errors += "<br> Every PO line requires an item";
                if (line.getQuantity() == null || line.getQuantity().compareTo(BigDecimal.ZERO) <= 0)
                    errors += "<br> PO item quantity must be greater than zero";
                else if (line.getQuantity().compareTo(new BigDecimal("999999.99")) > 0)
                    errors += "<br> PO item quantity exceeds the database limit";
                if (line.getUnitprice() == null || line.getUnitprice().compareTo(BigDecimal.ZERO) < 0)
                    errors += "<br> PO item unit price cannot be negative";
                else if (line.getUnitprice().compareTo(new BigDecimal("99999999.99")) > 0)
                    errors += "<br> PO item unit price exceeds the database limit";
                if (line.getQuantity() != null && line.getUnitprice() != null &&
                        line.getQuantity().multiply(line.getUnitprice())
                                .compareTo(new BigDecimal("999999.99")) > 0)
                    errors += "<br> PO item subtotal exceeds the database limit";
            }
        }
        return errors;
    }

    private void prepareItemsAndTotal(Purchaseorder purchaseorder) {
        BigDecimal total = BigDecimal.ZERO;
        for (Poitem line : purchaseorder.getPoitems()) {
            line.setPurchaseorder(purchaseorder);
            line.setUnitprice(line.getUnitprice().setScale(2, RoundingMode.HALF_UP));
            line.setSubTotal(line.getQuantity().multiply(line.getUnitprice())
                    .setScale(2, RoundingMode.HALF_UP));
            total = total.add(line.getSubTotal());
        }
        purchaseorder.setTotalAmount(total.setScale(2, RoundingMode.HALF_UP));
    }

    private User findLoggedUser(Authentication authentication) {
        return authentication == null ? null : userDao.findByUsername(authentication.getName());
    }

    private HashMap<String, String> response(Integer id, String poNumber, String errors) {
        HashMap<String, String> response = new HashMap<>();
        if (!errors.isEmpty()) errors = "Server Validation Errors: <br>" + errors;
        response.put("id", String.valueOf(id));
        response.put("url", "/purchaseorders/" + id);
        response.put("poNumber", poNumber == null ? "" : poNumber);
        response.put("errors", errors);
        return response;
    }
}
