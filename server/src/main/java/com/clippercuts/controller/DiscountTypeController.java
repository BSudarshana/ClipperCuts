package com.clippercuts.controller;

import com.clippercuts.dao.DiscountTypesDao;
import com.clippercuts.entity.Discounttype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/discounttypes")
public class DiscountTypeController {

    @Autowired
    private DiscountTypesDao discountTypesDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Discounttype> get() {

        List<Discounttype> discounttypes = this.discountTypesDao.findAll();

        discounttypes = discounttypes.stream().map(
                discounttype -> { Discounttype Distyp= new Discounttype();
                    Distyp.setId(discounttype.getId());
                    Distyp.setName(discounttype.getName());
                    return Distyp; }
        ).collect(Collectors.toList());

        return discounttypes;

    }

}


