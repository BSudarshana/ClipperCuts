package com.clippercuts.controller;

import com.clippercuts.dao.*;
import com.clippercuts.dto.*;
import com.clippercuts.util.GoodReceiveNoteService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin @RestController @RequestMapping("/grns")
public class GoodReceiveNoteController {
    private final GoodReceiveNoteDao dao;
    private final GoodReceiveNoteService service;
    private final InventoryLocationDao locationDao;

    public GoodReceiveNoteController(
            GoodReceiveNoteDao dao,
            GoodReceiveNoteService service,
            InventoryLocationDao locationDao)
    {
        this.dao=dao;this.service=service;
        this.locationDao=locationDao;
    }

    @GetMapping public List<GrnResponse> all(){return dao.findAll().stream().sorted(Comparator.comparing(com.clippercuts.entity.GoodReceiveNote::getId).reversed()).map(GrnResponse::new).collect(Collectors.toList());}
    @GetMapping("/{id}") public GrnResponse one(@PathVariable Integer id){return new GrnResponse(dao.findDetailedById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"GRN not found")));}
    @GetMapping("/eligible-purchaseorders") public List<GrnPurchaseOrderResponse> eligible(){return service.eligiblePurchaseOrders();}
    @GetMapping("/locations") public List<LookupResponse> locations(){return locationDao.findAll().stream().map(x->new LookupResponse(x.getId(),x.getName())).collect(Collectors.toList());}
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Map<String,String> add(@Valid @RequestBody GrnCreateRequest request,Principal principal){
        if(principal==null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication required");
        com.clippercuts.entity.GoodReceiveNote grn=service.receive(request,principal.getName());
        Map<String,String> response=new HashMap<>(); response.put("id",String.valueOf(grn.getId())); response.put("grnNumber",grn.getGrnNumber()); response.put("message","Goods receipt completed successfully"); return response;
    }
}
