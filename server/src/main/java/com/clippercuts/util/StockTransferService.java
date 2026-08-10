package com.clippercuts.util;

import com.clippercuts.dao.*;
import com.clippercuts.dto.StockTransferItemRequest;
import com.clippercuts.dto.StockTransferRequest;
import com.clippercuts.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class StockTransferService {

    private final StocktransferDao stocktransferDao;
    private final TransferitemDao transferitemDao;
    private final InventorytransactionDao transactionDao;
    private final ItemstockLocationDao stockDao;
    private final InventoryItemDao itemDao;
    private final InventoryLocationDao locationDao;
    private final EmployeeDao employeeDao;
    private final UserDao userDao;

    public StockTransferService(
            StocktransferDao stocktransferDao,
            TransferitemDao transferitemDao,
            InventorytransactionDao transactionDao,
            ItemstockLocationDao stockDao,
            InventoryItemDao itemDao,
            InventoryLocationDao locationDao,
            EmployeeDao employeeDao,
            UserDao userDao) {

        this.stocktransferDao = stocktransferDao;
        this.transferitemDao = transferitemDao;
        this.transactionDao = transactionDao;
        this.stockDao = stockDao;
        this.itemDao = itemDao;
        this.locationDao = locationDao;
        this.employeeDao = employeeDao;
        this.userDao = userDao;
    }

    @Transactional
    public Stocktransfer transfer(
            StockTransferRequest request,
            String username) {

        if (request.getFromLocationId()
                .equals(request.getToLocationId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Source and destination locations cannot be the same"
            );
        }

        User loggedUser = userDao.findByUsername(username);

        if (loggedUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Logged-in user not found"
            );
        }

        Location fromLocation = locationDao
                .findById(request.getFromLocationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Source location not found"
                ));

        Location toLocation = locationDao
                .findById(request.getToLocationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Destination location not found"
                ));

        Employee employee = employeeDao
                .findById(request.getEmployeeId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Employee not found"
                ));

        // Do not allow the same item twice.
        Set<Integer> itemIds = new HashSet<>();

        for (StockTransferItemRequest line : request.getItems()) {
            if (!itemIds.add(line.getItemId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The same product cannot be added twice"
                );
            }

            ItemstockLocation sourceStock = stockDao.findForUpdate(
                    line.getItemId(),
                    request.getFromLocationId()
            ).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Product is not available at the source location"
            ));

            BigDecimal available = sourceStock.getQuantity() == null
                    ? BigDecimal.ZERO
                    : sourceStock.getQuantity();

            if (available.compareTo(line.getQuantity()) < 0) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Insufficient stock for product: "
                                + sourceStock.getItem().getName()
                );
            }
        }

        Stocktransfer transfer = new Stocktransfer();
        transfer.setTransferdate(Date.valueOf(LocalDate.now()));
        transfer.setNote(request.getNote());
        transfer.setLocationfrom(fromLocation);
        transfer.setLocationto(toLocation);
        transfer.setEmployee(employee);
        transfer.setCreatedByUser(loggedUser);

        transfer = stocktransferDao.save(transfer);

        for (StockTransferItemRequest line : request.getItems()) {

            Item item = itemDao.findById(line.getItemId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Product not found"
                    ));

            ItemstockLocation source = stockDao.findForUpdate(
                    item.getId(),
                    fromLocation.getId()
            ).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Source stock not found"
            ));

            BigDecimal sourceBalance =
                    source.getQuantity().subtract(line.getQuantity());

            source.setQuantity(sourceBalance);
            source.setLastupdate(
                    new Timestamp(System.currentTimeMillis())
            );

            stockDao.save(source);

            ItemstockLocation destination = stockDao.findForUpdate(
                    item.getId(),
                    toLocation.getId()
            ).orElse(null);

            if (destination == null) {
                destination = new ItemstockLocation();
                destination.setItem(item);
                destination.setLocation(toLocation);
                destination.setQuantity(BigDecimal.ZERO);
            }

            BigDecimal destinationBalance =
                    destination.getQuantity().add(line.getQuantity());

            destination.setQuantity(destinationBalance);
            destination.setLastupdate(
                    new Timestamp(System.currentTimeMillis())
            );

            stockDao.save(destination);

            Transferitem transferitem = new Transferitem();
            transferitem.setStocktransfer(transfer);
            transferitem.setItem(item);
            transferitem.setQuantity(line.getQuantity());

            transferitemDao.save(transferitem);

            createTransaction(
                    transfer,
                    item,
                    fromLocation,
                    loggedUser,
                    "TRANSFER_OUT",
                    line.getQuantity().negate(),
                    sourceBalance
            );

            createTransaction(
                    transfer,
                    item,
                    toLocation,
                    loggedUser,
                    "TRANSFER_IN",
                    line.getQuantity(),
                    destinationBalance
            );
        }

        return transfer;
    }

    private void createTransaction(
            Stocktransfer transfer,
            Item item,
            Location location,
            User user,
            String type,
            BigDecimal quantity,
            BigDecimal balanceAfter) {

        Inventorytransaction transaction =
                new Inventorytransaction();

        transaction.setTransactiondate(Instant.now());
        transaction.setTransactiontype(type);
        transaction.setQuantity(quantity);
        transaction.setBalanceafter(balanceAfter);
        transaction.setItem(item);
        transaction.setLocation(location);
        transaction.setPerformedByUser(user);
        transaction.setStocktransfer(transfer);

        transaction.setDescription(
                "Stock Transfer #" + transfer.getId()
        );

        transactionDao.save(transaction);
    }
}