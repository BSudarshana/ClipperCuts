package com.clippercuts.util;

import com.clippercuts.dao.InventoryLocationDao;
import com.clippercuts.dao.InventorytransactionDao;
import com.clippercuts.dao.ItemstockLocationDao;
import com.clippercuts.dao.StockwriteoffDao;
import com.clippercuts.dao.StockwriteoffitemDao;
import com.clippercuts.dao.UserDao;
import com.clippercuts.dto.StockWriteOffItemRequest;
import com.clippercuts.dto.StockWriteOffRequest;
import com.clippercuts.entity.Inventorytransaction;
import com.clippercuts.entity.ItemstockLocation;
import com.clippercuts.entity.Location;
import com.clippercuts.entity.Stockwriteoff;
import com.clippercuts.entity.Stockwriteoffitem;
import com.clippercuts.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StockWriteOffService {

    private static final List<String> ALLOWED_REASONS = Arrays.asList(
            "Used for Services",
            "Finished/Empty",
            "Damaged",
            "Expired",
            "Missing/Stock Loss",
            "Other"
    );

    private final StockwriteoffDao writeOffDao;
    private final StockwriteoffitemDao writeOffItemDao;
    private final ItemstockLocationDao stockDao;
    private final InventorytransactionDao transactionDao;
    private final InventoryLocationDao locationDao;
    private final UserDao userDao;
    private final NumberService numberService;

    public StockWriteOffService(
            StockwriteoffDao writeOffDao,
            StockwriteoffitemDao writeOffItemDao,
            ItemstockLocationDao stockDao,
            InventorytransactionDao transactionDao,
            InventoryLocationDao locationDao,
            UserDao userDao,
            NumberService numberService) {

        this.writeOffDao = writeOffDao;
        this.writeOffItemDao = writeOffItemDao;
        this.stockDao = stockDao;
        this.transactionDao = transactionDao;
        this.locationDao = locationDao;
        this.userDao = userDao;
        this.numberService = numberService;
    }

    @Transactional
    public Stockwriteoff create(StockWriteOffRequest request, String username) {
        User loggedUser = userDao.findByUsername(username);
        if (loggedUser == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Logged-in user not found"
            );
        }

        Location location = locationDao.findById(request.getLocationId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Inventory location not found"
                ));

        String reason = request.getReason().trim();
        if (!ALLOWED_REASONS.contains(reason)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid stock write-off reason"
            );
        }

        Set<Integer> itemIds = new HashSet<>();
        for (StockWriteOffItemRequest line : request.getItems()) {
            if (!itemIds.add(line.getItemId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The same product cannot be added twice"
                );
            }

            ItemstockLocation stock = getLockedStock(
                    line.getItemId(),
                    location.getId()
            );

            BigDecimal available = safeQuantity(stock.getQuantity());
            if (line.getQuantity().compareTo(available) > 0) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Write-off quantity exceeds available stock for "
                                + stock.getItem().getName()
                );
            }
        }

        Stockwriteoff writeOff = new Stockwriteoff();
        writeOff.setWriteoffnumber(numberService.generateStockWriteOffNumber());
        writeOff.setWriteoffdate(Timestamp.from(Instant.now()));
        writeOff.setReason(reason);
        writeOff.setNote(clean(request.getNote()));
        writeOff.setLocation(location);
        writeOff.setCreatedByUser(loggedUser);
        writeOff = writeOffDao.save(writeOff);

        for (StockWriteOffItemRequest line : request.getItems()) {
            ItemstockLocation stock = getLockedStock(
                    line.getItemId(),
                    location.getId()
            );

            BigDecimal newBalance = stock.getQuantity().subtract(line.getQuantity());
            stock.setQuantity(newBalance);
            stock.setLastupdate(new Timestamp(System.currentTimeMillis()));
            stockDao.save(stock);

            Stockwriteoffitem writeOffItem = new Stockwriteoffitem();
            writeOffItem.setStockwriteoff(writeOff);
            writeOffItem.setItem(stock.getItem());
            writeOffItem.setQuantity(line.getQuantity());
            writeOffItemDao.save(writeOffItem);

            Inventorytransaction transaction = new Inventorytransaction();
            transaction.setTransactiondate(Instant.now());
            transaction.setTransactiontype("STOCK_WRITE_OFF");
            transaction.setQuantity(line.getQuantity().negate());
            transaction.setBalanceafter(newBalance);
            transaction.setDescription(
                    writeOff.getWriteoffnumber() + " - " + reason
            );
            transaction.setItem(stock.getItem());
            transaction.setLocation(location);
            transaction.setPerformedByUser(loggedUser);
            transactionDao.save(transaction);
        }

        return writeOff;
    }

    private ItemstockLocation getLockedStock(Integer itemId, Integer locationId) {
        return stockDao.findForUpdate(itemId, locationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Product is not available at the selected location"
                ));
    }

    private BigDecimal safeQuantity(BigDecimal quantity) {
        return quantity == null ? BigDecimal.ZERO : quantity;
    }

    private String clean(String value) {
        return value == null || value.trim().isEmpty()
                ? null
                : value.trim();
    }
}
