package lk.earth.earthuniversity.controller;


import lk.earth.earthuniversity.dao.SubcategoryDao;
import lk.earth.earthuniversity.entity.Subcategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/subcategories")
public class SubcategoryController {

    @Autowired
    private SubcategoryDao subcategoryDao;

    @GetMapping(path ="/list",produces = "application/json")
    public List<Subcategory> get() {

        List<Subcategory> subcategories = this.subcategoryDao.findAll();

        subcategories = subcategories.stream().map(
                subcategory -> { Subcategory m = new Subcategory();
                            m.setId(subcategory.getId());
                            m.setName(subcategory.getName());
                            return m; }
        ).collect(Collectors.toList());

        return subcategories;

    }

}


