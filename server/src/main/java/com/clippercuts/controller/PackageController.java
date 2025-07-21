package com.clippercuts.controller;

import com.clippercuts.dao.PackageDao;
import com.clippercuts.entity.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/packages")
public class PackageController {

    @Autowired
    private PackageDao packageDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Package> get(@RequestParam HashMap<String, String> params) {

        List<Package> packages = this.packageDao.findAll();

        if(params.isEmpty())  return packages;

        String pcknumber = params.get("packagnumber");
        String pckname = params.get("name");

        Stream<Package> packageStream = packages.stream();

        if(pcknumber!=null) packageStream = packageStream.filter(p -> p.getPackagnumber().contains(pcknumber));
        if(pckname!=null) packageStream = packageStream.filter(p -> p.getName().contains(pckname));

        return packageStream.collect(Collectors.toList());

    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Package servicePack){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(packageDao.findByPackageNumber(servicePack.getPackagnumber())!=null)
            errors = errors+"<br> Existing Package Number";
        if(packageDao.findByPackageName(servicePack.getName())!=null)
            errors = errors+"<br> Existing Package Name";

        if(errors=="")
            packageDao.save(servicePack);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(servicePack.getId()));
        responce.put("url","/packages/"+servicePack.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Package servicePack){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Package pack1 = packageDao.findByPackageNumber(servicePack.getPackagnumber());
        Package pack2 = packageDao.findByPackageName(servicePack.getName());

//        if(customer.getId().intValue() == customerdao.findByMyId() )
        if(pack1!=null && servicePack.getId()!=pack1.getId())
            errors = errors+"<br> Existing Package Number";
        if(pack2!=null && servicePack.getId()!=pack2.getId())
            errors = errors+"<br> Existing MPackage Name";

        if(errors=="") packageDao.save(servicePack);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(servicePack.getId()));
        responce.put("url","/customers/"+servicePack.getId());
        responce.put("errors",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Package Pack = packageDao.findByPackageId(id);

        if(Pack==null)
            errors = errors+"<br> Package Does Not Existed";

        if(errors=="") packageDao.delete(Pack);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/packages/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




