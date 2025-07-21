package com.clippercuts.controller;

import com.clippercuts.dao.PurchaseorderDao;
import com.clippercuts.entity.Purchaseorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/purchaseorders")
public class PurchaseorderController {

    @Autowired
    private PurchaseorderDao purchaseorderDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Purchaseorder> get(@RequestParam HashMap<String, String> params) {

        List<Purchaseorder> purchaseorder = this.purchaseorderDao.findAll();

        if(params.isEmpty())  return purchaseorder;

        String poNumber = params.get("itemnumber");

        Stream<Purchaseorder> purchaseorderStream = purchaseorder.stream();

        if(poNumber!=null) purchaseorderStream = purchaseorderStream.filter(p -> p.getPoNumber().contains(poNumber));

        return purchaseorderStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Purchaseorder purchaseorder){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(purchaseorderDao.findByPONumber(purchaseorder.getPoNumber())!=null)
            errors = errors+"<br> Existing PO Number";

        if(errors=="")
        purchaseorderDao.save(purchaseorder);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(purchaseorder.getId()));
        responce.put("url","/purchaseorders/"+purchaseorder.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Purchaseorder purchaseorder){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Purchaseorder po1 = purchaseorderDao.findByPONumber(purchaseorder.getPoNumber());

        if(po1!=null && purchaseorder.getId()!=po1.getId())
            errors = errors+"<br> Existing PO Number";

        if(errors=="") purchaseorderDao.save(purchaseorder);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(purchaseorder.getId()));
        responce.put("url","/purchaseorders/"+purchaseorder.getId());
        responce.put("errors",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Purchaseorder purchaseorder = purchaseorderDao.findPOById(id);

        if(purchaseorder==null)
            errors = errors+"<br> Item Does Not Existed";

        if(errors=="") purchaseorderDao.delete(purchaseorder);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/purchaseorders/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




