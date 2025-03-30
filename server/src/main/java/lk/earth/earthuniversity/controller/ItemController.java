package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.ItemDao;
import lk.earth.earthuniversity.entity.Employee;
import lk.earth.earthuniversity.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/items")
public class ItemController {

    @Autowired private ItemDao itemDao;

    @GetMapping(produces = "application/json")
    public List<Item> get(@RequestParam HashMap<String, String> params){
        List<Item> items = this.itemDao.findAll();

        if(params.isEmpty())  return items;

        String name = params.get("name");
        String code = params.get("code");
        String itemstatusid = params.get("itemstatusid");

        Stream<Item> itemStream = items.stream();

        if(name!=null) itemStream = itemStream.filter(item -> item.getName().equals(name));
        if(code!=null) itemStream = itemStream.filter(item -> item.getCode().equals(code));
        if(itemstatusid!=null) itemStream = itemStream.filter(item -> item.getItemstatus().getId()==Integer.parseInt(itemstatusid));


        return itemStream.collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Employee-Insert')")
    public HashMap<String,String> add(@RequestBody Item item){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(item.(employee.getNumber())!=null)
            errors = errors+"<br> Existing Number";

        return responce;
    }
}
