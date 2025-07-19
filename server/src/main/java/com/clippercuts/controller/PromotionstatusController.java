package com.clippercuts.controller;

import com.clippercuts.dao.PromotionStatusDao;
import com.clippercuts.entity.Promotionstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/promotionstatus")
public class PromotionstatusController {

    @Autowired
    private PromotionStatusDao promotionStatusDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Promotionstatus> get() {

        List<Promotionstatus> promotionstatuses = this.promotionStatusDao.findAll();

        promotionstatuses = promotionstatuses.stream().map(
                promotionstatus -> { Promotionstatus prostat= new Promotionstatus();
                    prostat.setId(promotionstatus.getId());
                    prostat.setName(promotionstatus.getName());
                    return prostat; }
        ).collect(Collectors.toList());

        return promotionstatuses;

    }

}


