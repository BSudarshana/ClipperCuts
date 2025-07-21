package com.clippercuts.controller;

import com.clippercuts.dao.GoodReceiveNoteDao;
import com.clippercuts.entity.GoodReceiveNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/grns")
public class GoodReceiveNoteController {

    @Autowired
    private GoodReceiveNoteDao goodReceiveNoteDao;

    @GetMapping(produces = "application/json")
//    @PreAuthorize("hasAuthority('customer-select')")p
    public List<GoodReceiveNote> get(@RequestParam HashMap<String, String> params) {

        List<GoodReceiveNote> goodReceiveNotes = this.goodReceiveNoteDao.findAll();

        if(params.isEmpty())  return goodReceiveNotes;

        String grnNumber = params.get("grn_number");

        Stream<GoodReceiveNote> goodReceiveNoteStream = goodReceiveNotes.stream();

        if(grnNumber!=null) goodReceiveNoteStream = goodReceiveNoteStream.filter(g -> g.getGrnNumber().contains(grnNumber));

        return goodReceiveNoteStream.collect(Collectors.toList());

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Insert')")
    public HashMap<String,String> add(@RequestBody GoodReceiveNote goodReceiveNote){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        if(goodReceiveNoteDao.findByGrnNumber(goodReceiveNote.getGrnNumber())!=null)
            errors = errors+"<br> Existing GRN Number";

        if(errors=="")
            goodReceiveNoteDao.save(goodReceiveNote);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(goodReceiveNote.getId()));
        responce.put("url","/grns/"+goodReceiveNote.getId());
        responce.put("errors",errors);

        return responce;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasAuthority('Customer-Update')")
    public HashMap<String,String> update(@RequestBody GoodReceiveNote goodReceiveNote){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        GoodReceiveNote grn1 = goodReceiveNoteDao.findByGrnNumber(goodReceiveNote.getGrnNumber());

        if(grn1!=null && goodReceiveNote.getId()!=grn1.getId())
            errors = errors+"<br> Existing GRN Number";

        if(errors=="") goodReceiveNoteDao.save(goodReceiveNote);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(goodReceiveNote.getId()));
        responce.put("url","/grns/"+goodReceiveNote.getId());
        responce.put("errors",errors);

        return responce;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String,String> delete(@PathVariable Integer id){

        HashMap<String,String> responce = new HashMap<>();
        String errors="";

        GoodReceiveNote goodReceiveNote = goodReceiveNoteDao.findByGrnId(id);

        if(goodReceiveNote==null)
            errors = errors+"<br> Item Does Not Existed";

        if(errors=="") goodReceiveNoteDao.delete(goodReceiveNote);
        else errors = "Server Validation Errors : <br> "+errors;

        responce.put("id",String.valueOf(id));
        responce.put("url","/grns/"+id);
        responce.put("errors",errors);

        return responce;
    }

}




