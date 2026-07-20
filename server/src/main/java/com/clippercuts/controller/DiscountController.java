package com.clippercuts.controller;

import com.clippercuts.dao.DiscountDao;
import com.clippercuts.entity.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping(value = "/discounts")
public class DiscountController {

    @Autowired
    private DiscountDao discountDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Discount> get(@RequestParam HashMap<String, String> params) {

        List<Discount> discounts = this.discountDao.findAll();

        if(params.isEmpty())  return discounts;

        String discountValue = params.get("value");

        Stream<Discount> discountStream = discounts.stream();

        if(discountValue!=null) discountStream = discountStream.filter(d -> d.getDiscountvalue().toString().contains(discountValue));

        return discountStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@Valid @RequestBody Discount discount){

        HashMap<String,String> response = new HashMap<>();
        String errors="";

//        if(discountDao.findByDiscountValue(discount.getValue())!=null)
//            errors = errors+"<br> Existing Discount Value: "+discount.getValue();

        try {
            Discount savedDiscount = discountDao.save(discount);

            response.put("id", String.valueOf(savedDiscount.getId()));
            response.put(
                    "url",
                    "/discounts/" + savedDiscount.getId()
            );
            response.put("errors", "");

        } catch (Exception e) {
            response.put("id", String.valueOf(discount.getId()));
            response.put("url", "");
            response.put(
                    "errors",
                    "Failed to update the discount: " + e.getMessage()
            );
        }

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Promotion-Update')")
    public HashMap<String,String> update(@RequestBody Discount discount){

        HashMap<String,String> response = new HashMap<>();

        // Validate the ID
        if (discount.getId() == null) {
            response.put("id", "");
            response.put("url", "");
            response.put("errors", "Discount ID is required");
            return response;
        }

        // Check whether the discount exists
        if (!discountDao.existsById(discount.getId())) {
            response.put("id", String.valueOf(discount.getId()));
            response.put("url", "");
            response.put("errors", "Discount does not exist");
            return response;
        }

        try {
            Discount savedDiscount = discountDao.save(discount);

            response.put("id", String.valueOf(savedDiscount.getId()));
            response.put(
                    "url",
                    "/discounts/" + savedDiscount.getId()
            );
            response.put("errors", "");

        } catch (Exception e) {
            response.put("id", String.valueOf(discount.getId()));
            response.put("url", "");
            response.put(
                    "errors",
                    "Failed to update the discount: " + e.getMessage()
            );
        }

        return response;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> response = new HashMap<>();
        String errors="";

        Discount discount = discountDao.findByDiscountById(id);

        if(discount==null)
            errors = errors+"<br> The Promotion Does Not Existed";

        if(errors.isEmpty()) discountDao.delete(discount);
        else errors = "Server Validation Errors : <br> "+errors;

        response.put("id",String.valueOf(id));
        response.put("url","/discount/"+id);
        response.put("errors",errors);

        return response;
    }

}




