package com.clippercuts.controller;

import com.clippercuts.dao.PaymentDao;
import com.clippercuts.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

    @Autowired
    private PaymentDao paymentDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Payment> get(@RequestParam HashMap<String, String> params) {

        List<Payment> payments = this.paymentDao.findAll();

        if(params.isEmpty())  return payments;

        String receiptnumber = params.get("receiptnumber");

        Stream<Payment> paymentStream = payments.stream();

        if(receiptnumber!=null) paymentStream = paymentStream.filter(p -> p.getReceiptnumber().contains(receiptnumber));

        return paymentStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Payment payment){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(paymentDao.findByReceiptnumber(payment.getReceiptnumber())!=null)
            errors = errors+"<br> Existing Receipt Number: "+payment.getReceiptnumber();

        if(errors=="")
            paymentDao.save(payment);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(payment.getId()));
        responce.put("url","/payments/"+payment.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Payment payment){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Payment payment1 = paymentDao.findByReceiptnumber(payment.getReceiptnumber());

        if(payment1!=null && payment.getId()!=payment1.getId())
            errors = errors+"<br> Existing Receipt Number : "+payment.getReceiptnumber();

        if(errors=="") paymentDao.save(payment);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(payment.getId()));
        responce.put("url ","/payments/"+payment.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Payment payment = paymentDao.findPaymentById(id);

        if(payment==null)
            errors = errors+"<br> The Payment Receipt  Does Not Existed";

        if(errors=="") paymentDao.delete(payment);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/payments/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




