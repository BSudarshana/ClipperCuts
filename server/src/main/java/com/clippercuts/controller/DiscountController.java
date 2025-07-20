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
        String promoStartDate = params.get("startdate");

        Stream<Discount> discountStream = discounts.stream();

        if(discountValue!=null) discountStream = discountStream.filter(d -> d.getValue().toString().contains(discountValue));

        return discountStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Discount discount){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(discountDao.findByDiscountValue(discount.getValue())!=null)
            errors = errors+"<br> Existing Discount Value: "+discount.getValue();

        if(errors=="")
            discountDao.save(discount);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(discount.getId()));
        responce.put("url","/disdounts/"+discount.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Discount discount){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Discount disc = discountDao.findByDiscountValue(discount.getValue());

        if(disc!=null && discount.getId()!=disc.getId())
            errors = errors+"<br> Existing Discount Value: "+discount.getValue();

        if(errors=="") discountDao.save(discount);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(discount.getId()));
        responce.put("url ","/promotions/"+discount.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Discount discount = discountDao.findByDiscountById(id);

        if(discount==null)
            errors = errors+"<br> The Promotion Does Not Existed";

        if(errors=="") discountDao.delete(discount);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/discount/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




