package com.clippercuts.controller;

import com.clippercuts.dao.EmployeeDao;
import com.clippercuts.dao.InventoryLocationDao;
import com.clippercuts.dao.ItemstockLocationDao;
import com.clippercuts.dao.StocktransferDao;
import com.clippercuts.dto.AvailableStockItemResponse;
import com.clippercuts.dto.LookupResponse;
import com.clippercuts.dto.StockTransferRequest;
import com.clippercuts.dto.StockTransferResponse;
import com.clippercuts.entity.Stocktransfer;
import com.clippercuts.util.StockTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/stocktransfers")
public class StocktransferController {

    private final StockTransferService stockTransferService;
    private final StocktransferDao stocktransferDao;
    private final InventoryLocationDao locationDao;
    private final EmployeeDao employeeDao;
    private final ItemstockLocationDao stockDao;

    public StocktransferController(
            StockTransferService stockTransferService,
            StocktransferDao stocktransferDao,
            InventoryLocationDao locationDao,
            EmployeeDao employeeDao,
            ItemstockLocationDao stockDao) {

        this.stockTransferService = stockTransferService;
        this.stocktransferDao = stocktransferDao;
        this.locationDao = locationDao;
        this.employeeDao = employeeDao;
        this.stockDao = stockDao;
    }

    @GetMapping(produces = "application/json")
    public List<StockTransferResponse> getAll() {

        return stocktransferDao.findAll()
                .stream()
                .map(StockTransferResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping(
            path = "/{id}",
            produces = "application/json"
    )
    public StockTransferResponse getById(
            @PathVariable Integer id) {

        Stocktransfer transfer = stocktransferDao.findDetailedById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Stock transfer not found"
                                ));

        return StockTransferResponse.from(transfer);
    }

    @GetMapping("/locations")
    public List<LookupResponse> getLocations() {

        return locationDao.findAll()
                .stream()
                .map(location ->
                        new LookupResponse(
                                location.getId(),
                                location.getName()
                        ))
                .collect(Collectors.toList());
    }

    @GetMapping("/employees")
    public List<LookupResponse> getEmployees() {

        return employeeDao.findAllNameId()
                .stream()
                .map(employee ->
                        new LookupResponse(
                                employee.getId(),
                                employee.getCallingname()
                        ))
                .collect(Collectors.toList());
    }

    @GetMapping("/available-items")
    public List<AvailableStockItemResponse> getAvailableItems(
            @RequestParam Integer locationId) {

        return stockDao.findAvailableByLocation(locationId)
                .stream()
                .map(AvailableStockItemResponse::from)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(
            @Valid @RequestBody StockTransferRequest request,
            Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }

        Stocktransfer transfer = stockTransferService.transfer(
                request,
                principal.getName()
        );

        HashMap<String, String> response = new HashMap<>();
        response.put("id", transfer.getId().toString());
        response.put(
                "message",
                "Stock transfer completed successfully"
        );

        return response;
    }
}