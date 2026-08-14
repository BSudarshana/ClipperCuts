package com.clippercuts.controller;

import com.clippercuts.dao.*;
import com.clippercuts.dto.*;
import com.clippercuts.entity.Invoice;
import com.clippercuts.util.ProductSaleService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/invoices/product-sales")
public class ProductSaleController {
    private final ProductSaleService service;
    private final InventoryLocationDao locationDao;
    private final ItemstockLocationDao stockDao;
    private final CustomerDao customerDao;

    public ProductSaleController(ProductSaleService s, InventoryLocationDao l, ItemstockLocationDao st, CustomerDao c) {
        service = s;
        locationDao = l;
        stockDao = st;
        customerDao = c;
    }

//    @GetMapping("/locations")
//    public List<LookupResponse> locations() {
//        return locationDao.findAll().stream().map(x -> new LookupResponse(x.getId(), x.getName()))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/locations")
    public List<LookupResponse> locations() {
        return locationDao.findByLocationtype_NameIgnoreCaseOrderByNameAsc("Retail")
                .stream()
                .map(x -> new LookupResponse(x.getId(), x.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/available-items")
    public List<AvailableSaleItemResponse> items(@RequestParam Integer locationId) {
        return stockDao.findAvailableByLocation(locationId).stream().map(AvailableSaleItemResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/customers")
    public List<Map<String, Object>> customers() {
        return customerDao.findAll().stream().map(c -> {
            Map<String, Object> x = new HashMap<>();
            x.put("id", c.getId());
            x.put("name", c.getFullname());
            x.put("mobile", c.getMobile());
            return x;
        }).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> add(@Valid @RequestBody ProductSaleRequest r, Principal p) {
        if (p == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        Invoice i = service.create(r, p.getName());
        Map<String, String> x = new HashMap<>();
        x.put("id", String.valueOf(i.getId()));
        x.put("invoicenumber", i.getInvoicenumber());
        x.put("message", "Product invoice created successfully");
        return x;
    }
}
