package com.clippercuts.controller;

import com.clippercuts.dao.InventoryDao;
import com.clippercuts.dao.InventoryItemDao;
import com.clippercuts.dao.InventoryLocationDao;
import com.clippercuts.dto.InventoryResponse;
import com.clippercuts.entity.Item;
import com.clippercuts.entity.ItemstockLocation;
import com.clippercuts.entity.Location;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryDao inventoryDao;
    private final InventoryItemDao inventoryItemDao;
    private final InventoryLocationDao inventoryLocationDao;

    public InventoryController(InventoryDao inventoryDao,
                               InventoryItemDao inventoryItemDao,
                               InventoryLocationDao inventoryLocationDao) {
        this.inventoryDao = inventoryDao;
        this.inventoryItemDao = inventoryItemDao;
        this.inventoryLocationDao = inventoryLocationDao;
    }

    @GetMapping(produces = "application/json")
    public List<InventoryResponse> get(
            @RequestParam(required = false) String itemnumber,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer locationId,
            @RequestParam(required = false) Boolean lowStock) {

        List<ItemstockLocation> stocks = inventoryDao.findAll();
        List<Item> items = inventoryItemDao.findAll();

        // ROP is stored on Item, so low-stock status must use the product total
        // across every location, not the quantity of a single location row.
        Map<Integer, BigDecimal> totals = stocks.stream().collect(Collectors.groupingBy(
                stock -> stock.getItem().getId(),
                LinkedHashMap::new,
                Collectors.reducing(
                        BigDecimal.ZERO,
                        stock -> stock.getQuantity() == null ? BigDecimal.ZERO : stock.getQuantity(),
                        BigDecimal::add
                )
        ));

        List<InventoryResponse> response = new ArrayList<>();
        for (ItemstockLocation stock : stocks) {
            InventoryResponse row = InventoryResponse.from(stock, totals.get(stock.getItem().getId()));

            if (itemnumber != null && !itemnumber.trim().isEmpty()
                    && (row.getItemnumber() == null || !row.getItemnumber().toLowerCase().contains(itemnumber.trim().toLowerCase()))) {
                continue;
            }
            if (name != null && !name.trim().isEmpty()
                    && (row.getItemName() == null || !row.getItemName().toLowerCase().contains(name.trim().toLowerCase()))) {
                continue;
            }
            if (locationId != null && !locationId.equals(row.getLocationId())) {
                continue;
            }
            if (lowStock != null && lowStock != row.isLowStock()) {
                continue;
            }
            response.add(row);
        }

        // A product that has never received stock has no itemstock_location row.
        // Keep it visible in Inventory as zero stock so it cannot be overlooked.
        for (Item item : items) {
            if (totals.containsKey(item.getId())) {
                continue;
            }
            InventoryResponse row = InventoryResponse.zeroStock(item);
            if (itemnumber != null && !itemnumber.trim().isEmpty()
                    && (row.getItemnumber() == null || !row.getItemnumber().toLowerCase().contains(itemnumber.trim().toLowerCase()))) {
                continue;
            }
            if (name != null && !name.trim().isEmpty()
                    && (row.getItemName() == null || !row.getItemName().toLowerCase().contains(name.trim().toLowerCase()))) {
                continue;
            }
            if (locationId != null) {
                continue;
            }
            if (lowStock != null && lowStock != row.isLowStock()) {
                continue;
            }
            response.add(row);
        }
        return response;
    }

    @GetMapping(path = "/locations", produces = "application/json")
    public List<Location> locations() {
        return inventoryLocationDao.findAll();
    }
}
