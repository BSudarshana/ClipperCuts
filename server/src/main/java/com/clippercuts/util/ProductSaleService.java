package com.clippercuts.util;

import com.clippercuts.dao.*;
import com.clippercuts.dto.*;
import com.clippercuts.entity.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.*;
import java.sql.*;
import java.time.Instant;
import java.util.*;

@Service
public class ProductSaleService {
 private final InvoiceDao invoiceDao;
 private final InvoiceItemDao lineDao;
 private final ItemstockLocationDao stockDao;
 private final InventorytransactionDao txDao;
 private final InventoryLocationDao locationDao;
 private final CustomerDao customerDao;
 private final PaymentStatusDao statusDao;
 private final UserDao userDao;
 private final NumberService numberService;

 public ProductSaleService(InvoiceDao invoiceDao, InvoiceItemDao lineDao, ItemstockLocationDao stockDao,
                           InventorytransactionDao txDao, InventoryLocationDao locationDao, CustomerDao customerDao,
                           PaymentStatusDao statusDao, UserDao userDao, NumberService numberService) {
  this.invoiceDao = invoiceDao;
  this.lineDao = lineDao;
  this.stockDao = stockDao;
  this.txDao = txDao;
  this.locationDao = locationDao;
  this.customerDao = customerDao;
  this.statusDao = statusDao;
  this.userDao = userDao;
  this.numberService = numberService;
 }

 @Transactional
 public Invoice create(ProductSaleRequest request, String username) {
  User user = userDao.findByUsername(username);
  if (user == null)
   fail(HttpStatus.UNAUTHORIZED, "Logged-in user was not found");
  Location location = locationDao.findById(request.getLocationId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory location not found"));
  Customer customer = request.getCustomerId() == null ? null
          : customerDao.findById(request.getCustomerId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
  Set<Integer> ids = new HashSet<>();
  List<SaleLine> lines = new ArrayList<>();
  BigDecimal total = BigDecimal.ZERO;
  for (ProductSaleItemRequest r : request.getItems()) {

   if (!ids.add(r.getItemId()))
    fail(HttpStatus.BAD_REQUEST, "The same product cannot be added twice");

   ItemstockLocation stock = stockDao.findForUpdate(r.getItemId(), location.getId())
           .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                   "Product is not available at the selected location"));

   BigDecimal available = stock.getQuantity() == null ? BigDecimal.ZERO : stock.getQuantity();
   if (r.getQuantity().compareTo(available) > 0)
    fail(HttpStatus.CONFLICT, "Insufficient stock for " + stock.getItem().getName());
   Item item = stock.getItem();

   if (item.getSprice() == null || item.getSprice().compareTo(BigDecimal.ZERO) < 0)
    fail(HttpStatus.CONFLICT, "Selling price is not configured for " + item.getName());

   BigDecimal sub = r.getQuantity().multiply(item.getSprice()).setScale(2, RoundingMode.HALF_UP);
   total = total.add(sub);

   lines.add(new SaleLine(stock, item, r.getQuantity(), sub));
  }
  BigDecimal discount = request.getDiscount() == null ? BigDecimal.ZERO
          : request.getDiscount().setScale(2, RoundingMode.HALF_UP);
  if (discount.compareTo(total) > 0)
   fail(HttpStatus.BAD_REQUEST, "Discount cannot exceed total amount");
  Paymentstatus unpaid = statusDao.findByNameIgnoreCase("Unpaid");
  if (unpaid == null)
   fail(HttpStatus.CONFLICT, "Payment status 'Unpaid' is not configured");
  Invoice invoice = new Invoice();
  invoice.setInvoicenumber(numberService.getLastInvoiceByYear());
  invoice.setInvoicedate(Timestamp.from(Instant.now()));
  invoice.setTotalamount(total);
  invoice.setDiscount(discount);
  invoice.setFinalAmount(total.subtract(discount));
  invoice.setPaymentstatus(unpaid);
  invoice.setAppointment(null);
  invoice.setPromotion(null);
  invoice.setCustomer(customer);
  invoice.setInvoicetype("PRODUCT_SALE");
  invoice.setCreatedByUser(user);
  invoice = invoiceDao.save(invoice);
  for (SaleLine x : lines) {
   BigDecimal balance = x.stock.getQuantity().subtract(x.quantity);
   x.stock.setQuantity(balance);
   x.stock.setLastupdate(new Timestamp(System.currentTimeMillis()));
   stockDao.save(x.stock);
   InvoiceItem line = new InvoiceItem();
   line.setInvoice(invoice);
   line.setItem(x.item);
   line.setLocation(location);
   line.setQuantity(x.quantity);
   line.setPrice(x.item.getSprice());
   line.setDiscount(BigDecimal.ZERO);
   line.setSubtotal(x.subtotal);
   lineDao.save(line);
   Inventorytransaction tx = new Inventorytransaction();
   tx.setTransactiondate(Instant.now());
   tx.setTransactiontype("SALE_OUT");
   tx.setQuantity(x.quantity.negate());
   tx.setBalanceafter(balance);
   tx.setDescription("Product sale " + invoice.getInvoicenumber());
   tx.setItem(x.item);
   tx.setLocation(location);
   tx.setPerformedByUser(user);
   tx.setInvoice(invoice);
   txDao.save(tx);
  }
  return invoice;
 }

 private void fail(HttpStatus s, String m) {
  throw new ResponseStatusException(s, m);
 }

 private static class SaleLine {
  final ItemstockLocation stock;
  final Item item;
  final BigDecimal quantity, subtotal;

  SaleLine(ItemstockLocation s, Item i, BigDecimal q, BigDecimal t) {
   stock = s;
   item = i;
   quantity = q;
   subtotal = t;
  }
 }
}
