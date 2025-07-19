package com.clippercuts.controller;

import com.clippercuts.dao.ServicecategoryDao;
import com.clippercuts.entity.Servicecategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/servicecategories")
public class ServicecategoryController {

    @Autowired
    private ServicecategoryDao servicecategoryDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Servicecategory> get() {

        List<Servicecategory> servicecategories = this.servicecategoryDao.findAll();

        servicecategories = servicecategories.stream().map(
                servicecategory -> { Servicecategory sercat= new Servicecategory ();
                    sercat.setId(servicecategory.getId());
                    sercat.setName(servicecategory.getName());
                    return sercat; }
        ).collect(Collectors.toList());

        return servicecategories;

    }

}


