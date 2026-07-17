package com.clippercuts.controller;

import com.clippercuts.dao.AppointmentstatusDao;
import com.clippercuts.entity.Appointmentstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/appointmentstatuses")
public class AppointmentstatusController {

    @Autowired
    private AppointmentstatusDao appointmentstatusDao;

    @GetMapping(path = "/list", produces = "application/json")
    public List<Appointmentstatus> get() {
        List<Appointmentstatus> statuses = this.appointmentstatusDao.findAll();

        return statuses.stream().map(status -> {
            Appointmentstatus s = new Appointmentstatus();
            s.setId(status.getId());
            s.setName(status.getName());
            return s;
        }).collect(Collectors.toList());
    }

    // This is a test function
    @PostMapping
    public String add(@RequestBody Appointmentstatus appointmentstatus) {
        appointmentstatusDao.save(appointmentstatus);

        return "success";
    }
}