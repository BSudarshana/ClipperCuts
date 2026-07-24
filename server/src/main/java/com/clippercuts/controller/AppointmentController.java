package com.clippercuts.controller;

import com.clippercuts.dao.AppointmentDao;
import com.clippercuts.dto.AppointmentCreateRequest;
import com.clippercuts.entity.Appointment;
import com.clippercuts.util.AppointmentBookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentDao appointmentDao;
    private final AppointmentBookingService bookingService;

    public AppointmentController(AppointmentDao appointmentDao,
                                 AppointmentBookingService bookingService) {
        this.appointmentDao = appointmentDao;
        this.bookingService = bookingService;
    }

    @GetMapping(produces = "application/json")
    public List<Appointment> get() {
        return appointmentDao.findAll();
    }

//    @GetMapping(path="/{id}",produces = "application/json")
//    public Appointment getById(@PathVariable Integer id) {
//        return appointmentDao.findById(id);
//    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Appointment getById(@PathVariable Integer id) {
        return appointmentDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Appointment not found with ID: " + id
                ));
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@Valid @RequestBody AppointmentCreateRequest request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Appointment saved = bookingService.create(request);
            response.put("id", String.valueOf(saved.getId()));
            response.put("url", "/appointments/" + saved.getId());
            response.put("errors", "");
        } catch (RuntimeException exception) {
            response.put("id", "");
            response.put("url", "");
            response.put("errors", exception.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}")
    public HashMap<String, String> update(
            @PathVariable Integer id,
            @Valid @RequestBody AppointmentCreateRequest request) {

        HashMap<String, String> response = new HashMap<>();

        try {
            return bookingService.updateAppointment(id, request);

        } catch (Exception e) {
            response.put("id", String.valueOf(id));
            response.put("url", "/appointments/" + id);
            response.put("errors", e.getMessage());

            return response;
        }
    }

    @PutMapping("/{id}/status")
    public HashMap<String, String> updateStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> request) {

        HashMap<String, String> response = new HashMap<>();

        try {
            Integer statusId = request.get("statusId");

            if (statusId == null) {
                throw new IllegalArgumentException(
                        "Appointment status is required"
                );
            }

            return bookingService.updateAppointmentStatus(id, statusId);
        } catch (Exception e) {
            response.put("id", String.valueOf(id));
            response.put("url", "/appointments/" + id);
            response.put("errors", e.getMessage());
            return response;
        }
    }
}

