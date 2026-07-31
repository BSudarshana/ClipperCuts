package com.clippercuts.controller;

import com.clippercuts.dao.RatingDao;
import com.clippercuts.entity.Rating;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingDao ratingDao;

    public RatingController(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    @GetMapping(path = "/list", produces = "application/json")
    public List<Rating> getList() {
        return ratingDao.findAll();
    }
}