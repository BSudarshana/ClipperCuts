package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.ItembrandDao;
import lk.earth.earthuniversity.entity.Itembrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/itemsbrand")
public class ItembrandController {

    @Autowired
    private ItembrandDao itembrandDao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Itembrand> get() {

        List<Itembrand> itembrands = this.itembrandDao.findAll();

        itembrands = itembrands.stream().map(
                itembrand -> { Itembrand itmbns = new Itembrand();
                    itmbns.setId(itembrand.getId());
                    itmbns.setName(itembrand.getName());
                    return itmbns; }
        ).collect(Collectors.toList());

        return itembrands;

    }

}


