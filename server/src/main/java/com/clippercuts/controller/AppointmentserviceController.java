package com.clippercuts.controller;

import com.clippercuts.dao.AppointmentserviceDao;
import com.clippercuts.dao.UserDao;
import com.clippercuts.dto.AppointmentLineView;
import com.clippercuts.entity.Appointmentservice;
import com.clippercuts.entity.Employee;
import com.clippercuts.entity.User;
import com.clippercuts.util.AppointmentBookingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;

public class AppointmentserviceController {
    private final AppointmentBookingService bookingService;
    private final AppointmentserviceDao lineDao;
    private final UserDao userDao;

    public AppointmentserviceController(AppointmentBookingService bookingService,
                                        AppointmentserviceDao lineDao,
                                        UserDao userDao) {
        this.bookingService = bookingService;
        this.lineDao = lineDao;
        this.userDao = userDao;
    }

    @GetMapping("/available-employees")
    public List<Employee> availableEmployees(@RequestParam Integer serviceId,
                                             @RequestParam Date date,
                                             @RequestParam Time startTime) {
        return bookingService.availableEmployees(serviceId, date, startTime);
    }

    @GetMapping("/my-upcoming")
    public List<AppointmentLineView> myUpcoming() {
        return lineDao.findAssignedUpcoming(currentEmployee().getId()).stream()
                .map(AppointmentLineView::from).collect(java.util.stream.Collectors.toList());
    }

    @GetMapping("/pending")
    public List<AppointmentLineView> pending() {
        return lineDao.findPendingUpcoming().stream()
                .map(AppointmentLineView::from).collect(java.util.stream.Collectors.toList());
    }

    @PutMapping("/{id}/assign-to-me")
    public HashMap<String, String> assignToMe(@PathVariable Integer id) {
        return mutationResponse(() -> bookingService.assignToEmployee(id, currentEmployee()));
    }

    @PutMapping("/{id}/start")
    public HashMap<String, String> start(@PathVariable Integer id) {
        return mutationResponse(() -> bookingService.start(id, currentEmployee()));
    }

    private Employee currentEmployee() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUsername(username);
        if (user == null) throw new IllegalArgumentException("Authenticated user was not found");
        return user.getEmployee();
    }

    private HashMap<String, String> mutationResponse(LineMutation mutation) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Appointmentservice line = mutation.run();
            response.put("id", String.valueOf(line.getId()));
            response.put("url", "/appointmentservices/" + line.getId());
            response.put("errors", "");
        } catch (RuntimeException exception) {
            response.put("id", "");
            response.put("url", "");
            response.put("errors", exception.getMessage());
        }
        return response;
    }

    private interface LineMutation { Appointmentservice run(); }
}
