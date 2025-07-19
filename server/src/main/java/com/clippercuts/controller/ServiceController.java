package com.clippercuts.controller;

import com.clippercuts.dao.ServiceDao;
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
@RequestMapping(value = "/services")
public class ServiceController {

    @Autowired
    private ServiceDao serviceDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Service> get(@RequestParam HashMap<String, String> params) {

        List<Service> services = this.serviceDao.findAll();

        if(params.isEmpty())  return services;

        String servicecode = params.get("code");;
        String serviceName = params.get("serviceName");

        Stream<Service> serviceStream = services.stream();

        if(servicecode!=null) serviceStream = serviceStream.filter(s -> s.getCode().contains(servicecode));
        if(serviceName!=null) serviceStream = serviceStream.filter(s -> s.getName().contains(serviceName));

        return serviceStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Service service){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(serviceDao.findByServiceCode(service.getCode())!=null)
            errors = errors+"<br> Existing Service Code";
        if(serviceDao.findByServiceName(service.getName())!=null)
            errors = errors+"<br> Existing Service Name";

        if(errors=="")
            serviceDao.save(service);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(service.getId()));
        responce.put("url","/services/"+service.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Service service){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Service service1 = serviceDao.findByServiceCode(service.getCode());
        Service service2 = serviceDao.findByServiceName(service.getName());

        if(service1!=null && service.getId()!=service1.getId())
            errors = errors+"<br> Existing Code";
        if(service2!=null && service.getId()!=service2.getId())
            errors = errors+"<br> Existing Service Name";

        if(errors=="") serviceDao.save(service);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(service.getId()));
        responce.put("url ","/services/"+service.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Service service = serviceDao.findByServiceId(id);

        if(service==null)
            errors = errors+"<br> The Service Does Not Existed";

        if(errors=="") serviceDao.delete(service);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/service/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




