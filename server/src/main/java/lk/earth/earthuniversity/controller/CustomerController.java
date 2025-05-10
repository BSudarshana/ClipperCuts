package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.CustomerDao;
import lk.earth.earthuniversity.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerdao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Customer> get(@RequestParam HashMap<String, String> params) {

        List<Customer> customer = this.customerdao.findAll();

        if(params.isEmpty())  return customer;

        String code = params.get("code");
        String fullname= params.get("fullname");
        String mobile= params.get("mobile");

        Stream<Customer> cstream = customer.stream();

        if(code!=null) cstream = cstream.filter(c -> c.getCode().contains(code));
        if(fullname!=null) cstream = cstream.filter(c -> c.getFullname().contains(fullname));
        if(mobile!=null) cstream = cstream.filter(c -> c.getMobile().contains(mobile));

        return cstream.collect(Collectors.toList());

    }

    @GetMapping(path ="/list",produces = "application/json")
    public List<Customer> get() {

        List<Customer> customers = this.customerdao.findAllNameId();

        customers = customers.stream().map(
                customer -> {
                    Customer c = new Customer(customer.getId(), customer.getCallingname());
                    return  c;
                }
        ).collect(Collectors.toList());

        return customers;

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Customer customer){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(customerdao.findByCode(customer.getCode())!=null)
            errors = errors+"<br> Existing Number";
        if(customerdao.findByMobile(customer.getMobile())!=null)
            errors = errors+"<br> Existing Mobile Number";

        if(errors=="")
        customerdao.save(customer);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(customer.getId()));
        responce.put("url","/customers/"+customer.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Customer customer){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Customer cus1 = customerdao.findByCode(customer.getCode());
        Customer cus2 = customerdao.findByMobile(customer.getMobile());

//        if(customer.getId().intValue() == customerdao.findByMyId() )
        if(cus1!=null && customer.getId()!=cus1.getId())
            errors = errors+"<br> Existing Code";
        if(cus2!=null && customer.getId()!=cus2.getId())
            errors = errors+"<br> Existing Mobile Number";

        if(errors=="") customerdao.save(customer);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(customer.getId()));
        responce.put("url","/customers/"+customer.getId());
        responce.put("errors",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Customer cus = customerdao.findByMyId(id);

        if(cus==null)
            errors = errors+"<br> Customer Does Not Existed";

        if(errors=="") customerdao.delete(cus);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/customers/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




