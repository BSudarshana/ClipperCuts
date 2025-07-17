package com.clippercuts.controller;

import com.clippercuts.dao.SupplierstatesDao;
import com.clippercuts.entity.Supplierstate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/supplierstates")
public class SupplierstatesController {

    @Autowired
    private SupplierstatesDao supstatesDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Supplierstate> get() {

        List<Supplierstate> supstates = this.supstatesDao.findAll();

        supstates = supstates.stream().map(
                cusstatus -> { Supplierstate supst = new Supplierstate();
                    supst.setId(cusstatus.getId());
                    supst.setName(cusstatus.getName());
                    return supst; }
        ).collect(Collectors.toList());

        return supstates;

    }

}


