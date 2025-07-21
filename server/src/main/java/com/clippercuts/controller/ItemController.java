package com.clippercuts.controller;

import com.clippercuts.dao.ItemDao;
import com.clippercuts.entity.Item;
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

    @Autowired
    private ItemDao itemDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<Item> get(@RequestParam HashMap<String, String> params) {

        List<Item> items = this.itemDao.findAll();

        if(params.isEmpty())  return items;

        String itemnumber = params.get("itemnumber");
        String itemname = params.get("name");


        Stream<Item> itemStream = items.stream();

        if(itemnumber!=null) itemStream = itemStream.filter(i -> i.getItemnumber().contains(itemnumber));
        if(itemname!=null) itemStream = itemStream.filter(i -> i.getName().contains(itemname));

        return itemStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody Item item){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(itemDao.findByItemNumber(item.getItemnumber())!=null)
            errors = errors+"<br> Existing Item Number";
        if(itemDao.findItemByName(item.getName())!=null)
            errors = errors+"<br> Existing Item ";

        if(errors=="")
        itemDao.save(item);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(item.getId()));
        responce.put("url","/items/"+item.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody Item item){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Item item1 = itemDao.findByItemNumber(item.getItemnumber());
        Item item2 = itemDao.findItemByName(item.getName());

//        if(customer.getId().intValue() == customerdao.findByMyId() )
        if(item1!=null && item.getId()!=item1.getId())
            errors = errors+"<br> Existing Item Number";
        if(item2!=null && item.getId()!=item2.getId())
            errors = errors+"<br> Existing Item Name";

        if(errors=="") itemDao.save(item);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(item.getId()));
        responce.put("url","/items/"+item.getId());
        responce.put("errors",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        System.out.println(id);

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        Item item = itemDao.findItemById(id);

        if(item==null)
            errors = errors+"<br> Item Does Not Existed";

        if(errors=="") itemDao.delete(item);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/items/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




