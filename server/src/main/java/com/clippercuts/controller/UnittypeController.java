package com.clippercuts.controller;

import com.clippercuts.dao.UnittypeDao;
import com.clippercuts.entity.Unittype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/unittypes")
public class UnittypeController {

    @Autowired
    private UnittypeDao unittypeDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Unittype> get() {

        List<Unittype> unittypes = this.unittypeDao.findAll();

        unittypes = unittypes.stream().map(
                utype -> { Unittype unttyp = new Unittype();
                    unttyp.setId(utype.getId());
                    unttyp.setName(utype.getName());
                    return unttyp; }
        ).collect(Collectors.toList());

        return unittypes;

    }

}


