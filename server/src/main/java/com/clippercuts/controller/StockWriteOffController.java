package com.clippercuts.controller;

import com.clippercuts.dao.InventoryLocationDao;
import com.clippercuts.dao.ItemstockLocationDao;
import com.clippercuts.dao.StockwriteoffDao;
import com.clippercuts.dto.LookupResponse;
import com.clippercuts.dto.StockWriteOffAvailableItemResponse;
import com.clippercuts.dto.StockWriteOffRequest;
import com.clippercuts.dto.StockWriteOffResponse;
import com.clippercuts.entity.Stockwriteoff;
import com.clippercuts.util.StockWriteOffService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/stockwriteoffs")
public class StockWriteOffController {

    private final StockWriteOffService writeOffService;
    private final StockwriteoffDao writeOffDao;
    private final InventoryLocationDao locationDao;
    private final ItemstockLocationDao stockDao;

    public StockWriteOffController(
            StockWriteOffService writeOffService,
            StockwriteoffDao writeOffDao,
            InventoryLocationDao locationDao,
            ItemstockLocationDao stockDao) {

        this.writeOffService = writeOffService;
        this.writeOffDao = writeOffDao;
        this.locationDao = locationDao;
        this.stockDao = stockDao;
    }

    @GetMapping
    public List<StockWriteOffResponse> getAll() {
        return writeOffDao.findAllDetailed()
                .stream()
                .map(StockWriteOffResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public StockWriteOffResponse getById(@PathVariable Integer id) {
        Stockwriteoff writeOff = writeOffDao.findDetailedById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Stock write-off not found"
                ));

        return StockWriteOffResponse.from(writeOff);
    }

    @GetMapping("/locations")
    public List<LookupResponse> getLocations() {
        return locationDao.findAll()
                .stream()
                .map(location -> new LookupResponse(
                        location.getId(),
                        location.getName()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/available-items")
    public List<StockWriteOffAvailableItemResponse> getAvailableItems(
            @RequestParam Integer locationId) {

        return stockDao.findAvailableByLocation(locationId)
                .stream()
                .map(StockWriteOffAvailableItemResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> add(
            @Valid @RequestBody StockWriteOffRequest request,
            Principal principal) {

        if (principal == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }

        Stockwriteoff writeOff = writeOffService.create(
                request,
                principal.getName()
        );

        Map<String, String> response = new HashMap<>();
        response.put("id", writeOff.getId().toString());
        response.put("writeoffnumber", writeOff.getWriteoffnumber());
        response.put("message", "Stock write-off completed successfully");
        return response;
    }
}
