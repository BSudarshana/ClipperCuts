package com.clippercuts.controller;

import com.clippercuts.dao.*;
import com.clippercuts.dto.ProductRequest;
import com.clippercuts.dto.ProductResponse;
import com.clippercuts.entity.Item;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemDao itemDao;
    private final ItemstatusDao itemstatusDao;
    private final UnittypeDao unittypeDao;
    private final ItembrandDao itembrandDao;
    private final SubcategoryDao subcategoryDao;
    private final ItemstockLocationDao stockDao;

    public ItemController(ItemDao itemDao, ItemstatusDao itemstatusDao, UnittypeDao unittypeDao,
                          ItembrandDao itembrandDao, SubcategoryDao subcategoryDao,
                          ItemstockLocationDao stockDao) {
        this.itemDao = itemDao; this.itemstatusDao = itemstatusDao;
        this.unittypeDao = unittypeDao; this.itembrandDao = itembrandDao;
        this.subcategoryDao = subcategoryDao; this.stockDao = stockDao;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<ProductResponse> get(@RequestParam HashMap<String, String> params) {
        Stream<Item> stream = itemDao.findAll().stream();
        String number = params.get("itemnumber"); String name = params.get("name");
        String statusId = params.get("statusId"); String categoryId = params.get("categoryId");
        if (number != null && !number.trim().isEmpty()) stream = stream.filter(i -> i.getItemnumber().toLowerCase().contains(number.trim().toLowerCase()));
        if (name != null && !name.trim().isEmpty()) stream = stream.filter(i -> i.getName().toLowerCase().contains(name.trim().toLowerCase()));
        if (statusId != null && !statusId.isEmpty()) stream = stream.filter(i -> i.getItemstatus().getId() == Integer.parseInt(statusId));
        if (categoryId != null && !categoryId.isEmpty()) stream = stream.filter(i -> i.getSubcategory().getCategory().getId() == Integer.parseInt(categoryId));
        return stream.map(this::response).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ProductResponse getOne(@PathVariable Integer id) { return response(find(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public HashMap<String, String> add(@Valid @RequestBody ProductRequest request) {
        itemDao.findByItemnumberIgnoreCase(request.getItemnumber().trim()).ifPresent(i -> conflict("Existing item number"));
        itemDao.findByNameIgnoreCase(request.getName().trim()).ifPresent(i -> conflict("Existing item name"));
        Item item = new Item();
        apply(item, request);
//        item.setQuantity(BigDecimal.ZERO);
        itemDao.save(item);
        return success(item.getId());
    }

    @PutMapping("/{id}")
    @Transactional
    public HashMap<String, String> update(@PathVariable Integer id, @Valid @RequestBody ProductRequest request) {
        Item item = find(id);
        if (itemDao.existsByItemnumberIgnoreCaseAndIdNot(request.getItemnumber().trim(), id)) conflict("Existing item number");
        if (itemDao.existsByNameIgnoreCaseAndIdNot(request.getName().trim(), id)) conflict("Existing item name");
        apply(item, request);
        itemDao.save(item);
        return success(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public HashMap<String, String> delete(@PathVariable Integer id) {
        Item item = find(id);
        if (stockDao.existsByItem_Id(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Products with stock records cannot be deleted; set the status to Inactive");
        itemDao.delete(item);
        return success(id);
    }

    private Item find(Integer id) { return itemDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist")); }
    private ProductResponse response(Item item) { return ProductResponse.fromEntity(item, stockDao.totalStock(item.getId())); }
    private void apply(Item i, ProductRequest r) {
        i.setItemnumber(r.getItemnumber().trim()); i.setName(r.getName().trim());
        i.setDointroduced(r.getDointroduced()); i.setSprice(r.getSprice()); i.setPprice(r.getPprice()); i.setRop(r.getRop());
        i.setItemstatus(itemstatusDao.findById(r.getItemstatusId()).orElseThrow(() -> missing("Item status")));
        i.setUnittype(unittypeDao.findById(r.getUnittypeId()).orElseThrow(() -> missing("Unit type")));
        i.setItembrand(itembrandDao.findById(r.getItembrandId()).orElseThrow(() -> missing("Item brand")));
        i.setSubcategory(subcategoryDao.findById(r.getSubcategoryId()).orElseThrow(() -> missing("Subcategory")));
    }
    private ResponseStatusException missing(String value) { return new ResponseStatusException(HttpStatus.NOT_FOUND, value + " does not exist"); }
    private void conflict(String message) { throw new ResponseStatusException(HttpStatus.CONFLICT, message); }
    private HashMap<String, String> success(Integer id) { HashMap<String, String> r = new HashMap<>(); r.put("id", String.valueOf(id)); r.put("url", "/items/" + id); r.put("errors", ""); return r; }
}
