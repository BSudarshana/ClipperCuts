package com.clippercuts.controller;

import com.clippercuts.dao.InvoiceDao;
import com.clippercuts.dao.ServiceDao;
import com.clippercuts.entity.Invoice;
import com.clippercuts.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceDao invoiceDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Invoice> get(@RequestParam HashMap<String, String> params) {

        List<Invoice> invoices = this.invoiceDao.findAll();

        if(params.isEmpty())  return invoices;

        String invoiceNumber = params.get("invoicenumber");
        String invoiceDate = params.get("invoicedate");

        Stream<Invoice> invoiceStream = invoices.stream();

        if(invoiceNumber!=null) invoiceStream = invoiceStream.filter(i -> i.getInvoicenumber().contains(invoiceNumber));
        if(invoiceDate!=null) invoiceStream = invoiceStream.filter(i -> i.getInvoicedate().toString().contains(invoiceDate));

        return invoiceStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Invoice invoice){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(invoiceDao.findByInvoiceNumber(invoice.getInvoicenumber())!=null)
            errors = errors+"<br> Existing Invoice Number";

        if(errors=="")
            invoiceDao.save(invoice);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(invoice.getId()));
        responce.put("url","/invoices/"+invoice.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Invoice invoice){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Invoice invoice1 = invoiceDao.findByInvoiceNumber(invoice.getInvoicenumber());

        if(invoice1!=null && invoice.getId()!=invoice1.getId())
            errors = errors+"<br> Existing Invoice Number";

        if(errors=="") invoiceDao.save(invoice);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(invoice.getId()));
        responce.put("url ","/invoices/"+invoice.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Invoice invoice = invoiceDao.findInvoiceById(id);

        if(invoice==null)
            errors = errors+"<br> The Invoice Does Not Existed";

        if(errors=="") invoiceDao.delete(invoice);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/invoices/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




