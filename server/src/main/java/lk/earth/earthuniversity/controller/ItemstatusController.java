package lk.earth.earthuniversity.controller;


import lk.earth.earthuniversity.dao.ItemstatusDao;
import lk.earth.earthuniversity.entity.Itemstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/itemstatus")
public class ItemstatusController {

    @Autowired
    private ItemstatusDao itemstatusDao;

    @GetMapping(path ="/list",produces = "application/json")
    public List<Itemstatus> get() {

        List<Itemstatus> itemstatus = this.itemstatusDao.findAll();

        itemstatus = itemstatus.stream().map(
                itemstatus1 -> { Itemstatus m = new Itemstatus();
                            m.setId(itemstatus1.getId());
                            m.setName(itemstatus1.getName());
                            return m; }
        ).collect(Collectors.toList());

        return itemstatus;

    }

}


