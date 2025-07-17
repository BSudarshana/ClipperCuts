package com.clippercuts.controller;

import com.clippercuts.dao.SuppliertypeDao;
import com.clippercuts.entity.Supplierstype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/supplierstypes")
public class SuppliertypeController {

    @Autowired
    private SuppliertypeDao suptypedao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Supplierstype> get() {

        List<Supplierstype> suptypes = this.suptypedao.findAll();

        suptypes = suptypes.stream().map(
                suptype -> { Supplierstype ct = new Supplierstype();
                    ct.setId(suptype.getId());
                    ct.setName(suptype.getName());
                    return ct; }
        ).collect(Collectors.toList());

        return suptypes;

    }

}


