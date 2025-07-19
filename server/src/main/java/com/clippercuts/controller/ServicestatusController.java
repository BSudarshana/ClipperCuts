package com.clippercuts.controller;

import com.clippercuts.dao.ServicestatusDao;
import com.clippercuts.entity.Servicestatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/servicestatuses")
public class ServicestatusController {

    @Autowired
    private ServicestatusDao servicestatusDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Servicestatus> get() {

        List<Servicestatus> servicestatuses = this.servicestatusDao.findAll();

        servicestatuses = servicestatuses.stream().map(
                servicestatus -> { Servicestatus sertus= new Servicestatus();
                    sertus.setId(servicestatus.getId());
                    sertus.setName(servicestatus.getName());
                    return sertus; }
        ).collect(Collectors.toList());

        return servicestatuses;

    }

}


