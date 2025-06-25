package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.SupplierDao;
import lk.earth.earthuniversity.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/suppliers")
public class SupplierController {

    @Autowired
    private SupplierDao supplierdao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Supplier> get(@RequestParam HashMap<String, String> params) {

        List<Supplier> supplier = this.supplierdao.findAll();

        if(params.isEmpty())  return supplier;

        String regnum = params.get("code");
        String name= params.get("name");
        String type= params.get("type");

        Stream<Supplier> cstream = supplier.stream();

        if(regnum!=null) cstream = cstream.filter(c -> c.getRegisternumber().contains(regnum));
        if(name!=null) cstream = cstream.filter(c -> c.getName().contains(name));
        if(type!=null) cstream = cstream.filter(c -> c.getSupplierstype().getName().contains(type));

        return cstream.collect(Collectors.toList());

    }

    @GetMapping(path ="/list",produces = "application/json")
    public List<Supplier> get() {

        List<Supplier> suppliers = this.supplierdao.findAll();

        suppliers = suppliers.stream().map(
                supplier -> {
                    Supplier c = new Supplier();
                    return  c;
                }
        ).collect(Collectors.toList());

        return suppliers;

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Supplier supplier){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

//        if(supplierdao.findRegNumber(supplier.getRegisternumber())!=null)
//            errors = errors+"<br> Existing Number";

//        if(supplierdao.findByMobile(supplier.getMobile())!=null)
//            errors = errors+"<br> Existing Mobile Number";

        if(errors=="")
        supplierdao.save(supplier);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(supplier.getId()));
        responce.put("url","/customers/"+supplier.getId());
        responce.put("errors",errors);

        return responce;
    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.CREATED)
////    @PreAuthorize("hasAuthority('Customer-Update')")
//    public HashMap<String,String> update(@RequestBody Customer customer){
//
//        HashMap<String,String> responce = new HashMap<>();
//        String errors="";
//
//        Customer cus1 = supplierdao.findByCode(customer.getCode());
//        Customer cus2 = supplierdao.findByMobile(customer.getMobile());
//
////        if(customer.getId().intValue() == supplierdao.findByMyId() )
//        if(cus1!=null && customer.getId()!=cus1.getId())
//            errors = errors+"<br> Existing Code";
//        if(cus2!=null && customer.getId()!=cus2.getId())
//            errors = errors+"<br> Existing Mobile Number";
//
//        if(errors=="") supplierdao.save(customer);
//        else errors = "Server Validation Errors : <br> "+errors;
//
//        responce.put("id",String.valueOf(customer.getId()));
//        responce.put("url","/customers/"+customer.getId());
//        responce.put("errors",errors);
//
//        return responce;
//    }
//
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public HashMap<String,String> delete(@PathVariable Integer id){
//
//        System.out.println(id);
//
//        HashMap<String,String> responce = new HashMap<>();
//        String errors="";
//
//        Customer cus = supplierdao.findByMyId(id);
//
//        if(cus==null)
//            errors = errors+"<br> Customer Does Not Existed";
//
//        if(errors=="") supplierdao.delete(cus);
//        else errors = "Server Validation Errors : <br> "+errors;
//
//        responce.put("id",String.valueOf(id));
//        responce.put("url","/customers/"+id);
//        responce.put("errors",errors);
//
//        return responce;
//    }

}




