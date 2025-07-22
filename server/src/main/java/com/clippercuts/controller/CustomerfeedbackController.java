package com.clippercuts.controller;

import com.clippercuts.dao.CustomerfeedbackDao;
import com.clippercuts.entity.Customerfeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/customerfeedbacks")
public class CustomerfeedbackController {

    @Autowired
    private CustomerfeedbackDao customerfeedbackDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Customerfeedback> get(@RequestParam HashMap<String, String> params) {

        List<Customerfeedback> customerfeedbacks = this.customerfeedbackDao.findAll();

        if(params.isEmpty())  return customerfeedbacks;

//        String customerName = params.get("qutext");

        Stream<Customerfeedback> cusfbackStream = customerfeedbacks.stream();

//        if(customerName!=null) cusfbackStream = cusfbackStream.filter(c -> c.getCustomer().getId().toString());

        return cusfbackStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Customerfeedback cusfback){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

//        if(customerfeedbackDao.findQuestionByText(question.getQutext())!=null)
//            errors = errors+"<br> Existing Question ";

        if(errors=="")
            customerfeedbackDao.save(cusfback);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(cusfback.getId()));
        responce.put("url","/customerfeedbacks/"+cusfback.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Customerfeedback cusfback){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        int cusId = cusfback.getCustomer().getId();
        List<Customerfeedback> cusfb = customerfeedbackDao.customerfeedbackByCusId(cusId);

        if(cusfb==null)
            errors = errors+"<br> invalid customer id";

        if(errors=="") customerfeedbackDao.save(cusfback);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(cusfback.getId()));
        responce.put("url ","/customerfeedbacks/"+cusfback.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Customerfeedback cusfb = customerfeedbackDao.customerfeedbackById(id);

        if(cusfb==null)
            errors = errors+"<br> The Payment Receipt  Does Not Existed";

        if(errors=="") customerfeedbackDao.delete(cusfb);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/customerfeedbacks/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




