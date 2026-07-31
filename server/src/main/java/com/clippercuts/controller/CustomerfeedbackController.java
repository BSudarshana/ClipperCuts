package com.clippercuts.controller;

import com.clippercuts.dao.CustomerfeedbackDao;
import com.clippercuts.dao.RatingDao;
import com.clippercuts.entity.Customerfeedback;
import com.clippercuts.entity.Rating;
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
    @Autowired
    private RatingDao ratingDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Customerfeedback> get(@RequestParam HashMap<String, String> params) {

        List<Customerfeedback> customerfeedbacks = this.customerfeedbackDao.findAll();

        if(params.isEmpty())  return customerfeedbacks;

        String customerId = params.get("id");

        Stream<Customerfeedback> cusfbackStream = customerfeedbacks.stream();

        if (customerId != null) {
            cusfbackStream = cusfbackStream.filter(c ->
                    c.getCustomer() != null
                            && c.getCustomer().getId() != null
                            && c.getCustomer().getId().toString().equals(customerId)
            );
        }

        return cusfbackStream.collect(Collectors.toList());

    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Rating> getList() {
        return ratingDao.findAll();
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String, String> add(@RequestBody Customerfeedback cusfback){

        HashMap<String, String> response = new HashMap<>();
        String errors="";

        if(errors.isEmpty()){
            customerfeedbackDao.save(cusfback);
        }
        else{
            errors = "Server Validation Errors : <br> "+errors;
        }

        response.put("id", String.valueOf(cusfback.getId()));
        response.put("url", "/customerfeedbacks/" + cusfback.getId());
        response.put("errors", errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Customerfeedback cusfback){

        HashMap<String, String> response = new HashMap<>();
        String errors="";

        int cusId = cusfback.getCustomer().getId();
        List<Customerfeedback> cusfb = customerfeedbackDao.customerfeedbackByCusId(cusId);

        if (cusfb == null || cusfb.isEmpty()) {
            errors += "<br> Invalid customer ID";
        }

        if (errors.isEmpty()) {
            customerfeedbackDao.save(cusfback);
        } else {
            errors = "Server Validation Errors: <br>" + errors;
        }

        response.put("id", String.valueOf(cusfback.getId()));
        response.put("url", "/customerfeedbacks/" + cusfback.getId());
        response.put("errors", errors);

        return response;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> delete(@PathVariable Integer id){

        HashMap<String, String> response = new HashMap<>();
        String errors="";

        Customerfeedback cusfb = customerfeedbackDao.customerfeedbackById(id);

        if(cusfb==null)
            errors = errors+"<br> The Customer feedback Does Not Existed";

        if(errors.isEmpty()){
            customerfeedbackDao.delete(cusfb);
        }
        else{
            errors = "Server Validation Errors : <br> "+errors;
        }

        response.put("id", String.valueOf(id));
        response.put("url", "/customerfeedbacks/" + id);
        response.put("errors", errors);

        return response;
    }

}




