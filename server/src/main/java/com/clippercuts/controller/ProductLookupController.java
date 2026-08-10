package com.clippercuts.controller;

import com.clippercuts.dao.CategoryDao;
import com.clippercuts.dao.SubcategoryDao;
import com.clippercuts.dao.UnittypeDao;
import com.clippercuts.entity.Category;
import com.clippercuts.entity.Subcategory;
import com.clippercuts.entity.Unittype;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
public class ProductLookupController {
    private final CategoryDao categoryDao;
    private final SubcategoryDao subcategoryDao;
    private final UnittypeDao unittypeDao;

    public ProductLookupController(CategoryDao categoryDao, SubcategoryDao subcategoryDao,
                                   UnittypeDao unittypeDao) {
        this.categoryDao = categoryDao;
        this.subcategoryDao = subcategoryDao;
        this.unittypeDao = unittypeDao;
    }

    @GetMapping("/categories/list")
    public List<Category> categories() { return categoryDao.findAll(); }

    @GetMapping("/subcategories/list")
    public List<Subcategory> subcategories(@RequestParam(required = false) Integer categoryId) {
        return categoryId == null ? subcategoryDao.findAll()
                : subcategoryDao.findByCategory_IdOrderByName(categoryId);
    }

    @GetMapping("/unittypes/list")
    public List<Unittype> unittypes() { return unittypeDao.findAll(); }
}
