package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.CustomerstatusDao;
import lk.earth.earthuniversity.dao.ItemstatusDao;
import lk.earth.earthuniversity.entity.Customerstatus;
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
@RequestMapping(value = "/itemsstatuses")
public class ItemstatusController {

    @Autowired
    private ItemstatusDao itemstatusdao;

    @GetMapping(path ="/list", produces = "application/json")
    public List<Itemstatus> get() {

        List<Itemstatus> itemstatuses = this.itemstatusdao.findAll();

        itemstatuses = itemstatuses.stream().map(
                itemstatus -> { Itemstatus itmsts = new Itemstatus();
                    itmsts.setId(itemstatus.getId());
                    itmsts.setName(itemstatus.getName());
                    return itmsts; }
        ).collect(Collectors.toList());

        return itemstatuses;

    }

}


