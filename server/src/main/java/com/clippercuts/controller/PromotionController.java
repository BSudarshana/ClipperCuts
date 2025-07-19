package com.clippercuts.controller;

import com.clippercuts.dao.PromotionDao;
import com.clippercuts.entity.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

        if(params.isEmpty())  return promotions;

        String promoTitle = params.get("title");
        String promoStartDate = params.get("startdate");

        Stream<Promotion> promotionStream = promotions.stream();

        if(promoTitle!=null) promotionStream = promotionStream.filter(p -> p.getTitle().contains(promoTitle));
        if(promoStartDate!=null) promotionStream  = promotionStream.filter(p -> p.getStartdate().toString().contains(promoStartDate));

        return promotionStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Promotion promotion){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(promotionDao.findByPromotionTitle(promotion.getTitle())!=null)
            errors = errors+"<br> Existing Promotion Title: "+promotion.getTitle();

        if(errors=="")
            promotionDao.save(promotion);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(promotion.getId()));
        responce.put("url","/promotions/"+promotion.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Promotion promotion){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Promotion promo = promotionDao.findByPromotionTitle(promotion.getTitle());

        if(promo!=null && promotion.getId()!=promo.getId())
            errors = errors+"<br> Existing Promotion Title: "+promotion.getTitle();

        if(errors=="") promotionDao.save(promotion);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id ",String.valueOf(promotion.getId()));
        responce.put("url ","/promotions/"+promotion.getId());
        responce.put("error ",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Promotion promotion = promotionDao.findByPromotionId(id);

        if(promotion==null)
            errors = errors+"<br> The Promotion Does Not Existed";

        if(errors=="") promotionDao.delete(promotion);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/service/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




