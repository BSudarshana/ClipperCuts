package com.clippercuts.controller;

import com.clippercuts.dao.ExpensecategoryDao;
import com.clippercuts.entity.Expensecategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/expensecategories")
public class ExpensecategoryController {
    @Autowired
    private ExpensecategoryDao expensecategoryDao;

    @GetMapping(path = "/list", produces = "application/json")
    public List<Expensecategory> getAllList() {
        return expensecategoryDao.findAll();
    }
}
