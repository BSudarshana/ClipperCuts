package com.clippercuts.controller;

import com.clippercuts.dao.PromotionDao;
import com.clippercuts.entity.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/promotions")
public class PromotionController {

    @Autowired
    private PromotionDao promotionDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Promotion> get(@RequestParam HashMap<String, String> params) {

        List<Promotion> promotions = this.promotionDao.findAll();

//        if(params.isEmpty())  return promotions;
//
//        String promoTitle = params.get("title");
//        String promoStartDate = params.get("startdate");
//
//        Stream<Promotion> promotionStream = promotions.stream();
//
//        if(promoTitle!=null) promotionStream = promotionStream.filter(p -> p.getTitle().contains(promoTitle));
//        if(promoStartDate!=null) promotionStream  = promotionStream.filter(p -> p.getStartdate().toString().contains(promoStartDate));
//
//        return promotionStream.collect(Collectors.toList());

        return promotions;

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@Valid @RequestBody Promotion promotion){

        HashMap<String,String> response = new HashMap<>();
        String errors="";

        if(promotionDao.findByPromotionTitle(promotion.getTitle())!=null)
            errors = errors+"<br> Existing Promotion Title: "+promotion.getTitle();

        if(errors.isEmpty())
            promotionDao.save(promotion);
        else errors = "Server Validation Errors : <br> "+errors;

        response.put("id",String.valueOf(promotion.getId()));
        response.put("url","/promotions/"+promotion.getId());
        response.put("errors",errors);

        return response;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,Object> update(@Valid @RequestBody Promotion promotion){

        HashMap<String, Object> response = new HashMap<>();
        String errors = "";

        // Check whether the promotion has an ID
        if (promotion.getId() == null) {
            errors += "<br>Promotion ID is required";
        } else {
            // Check whether the promotion exists in the database
            Promotion existingPromotion =
                    promotionDao.findById(promotion.getId()).orElse(null);

            if (existingPromotion == null) {
                errors += "<br>Promotion does not exist";
            }
        }

        if (errors.isEmpty()) {
            try {
                Promotion savedPromotion = promotionDao.save(promotion);

                response.put("id", savedPromotion.getId());
                response.put(
                        "url",
                        "/promotions/" + savedPromotion.getId()
                );
                response.put("errors", "");

            } catch (Exception e) {
                response.put("id", null);
                response.put("url", "");
                response.put(
                        "errors",
                        "Server error while updating the promotion"
                );
            }
        } else {
            response.put("id", promotion.getId());
            response.put("url", "");
            response.put("errors", errors);
        }

        return response;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> response = new HashMap<>();
        String errors="";

        Promotion promotion = promotionDao.findByPromotionId(id);

        if(promotion==null)
            errors = errors+"<br> The Promotion Does Not Existed";

        if(errors.isEmpty()) promotionDao.delete(promotion);
        else errors = "Server Validation Errors : <br> "+errors;

        response.put("id",String.valueOf(id));
        response.put("url","/service/"+id);
        response.put("errors",errors);

        return response;
    }

}




