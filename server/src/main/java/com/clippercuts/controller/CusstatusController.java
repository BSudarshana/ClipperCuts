package com.clippercuts.controller;

import com.clippercuts.dao.CustomerstatusDao;
import com.clippercuts.entity.Customerstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/customersstatuses")
public class CusstatusController {

    @Autowired
    private CustomerstatusDao cusstatusdao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Customerstatus> get() {

        List<Customerstatus> cusstatuss = this.cusstatusdao.findAll();

        cusstatuss = cusstatuss.stream().map(
                cusstatus -> { Customerstatus d = new Customerstatus();
                    d.setId(cusstatus.getId());
                    d.setName(cusstatus.getName());
                    return d; }
        ).collect(Collectors.toList());

        return cusstatuss;

    }

}


