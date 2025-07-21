package com.clippercuts.controller;

import com.clippercuts.dao.PostatusDao;
import com.clippercuts.entity.Postatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/postatuses")
public class PostatusController {

    @Autowired
    private PostatusDao postatusDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Postatus> get() {

        List<Postatus> postatuses = this.postatusDao.findAll();

        postatuses = postatuses.stream().map(
                postatus -> { Postatus potus= new Postatus();
                    potus.setId(postatus.getId());
                    potus.setName(postatus.getName());
                    return postatus; }
        ).collect(Collectors.toList());

        return postatuses;

    }

}


