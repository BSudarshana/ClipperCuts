package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.CustomertypeDao;
import lk.earth.earthuniversity.entity.Customertype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/customerstypes")
public class CustypeController {

    @Autowired
    private CustomertypeDao custypedao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Customertype> get() {

        List<Customertype> custypes = this.custypedao.findAll();

        custypes = custypes.stream().map(
                custype -> { Customertype ct = new Customertype();
                    ct.setId(custype.getId());
                    ct.setName(custype.getName());
                    return ct; }
        ).collect(Collectors.toList());

        return custypes;

    }

}


