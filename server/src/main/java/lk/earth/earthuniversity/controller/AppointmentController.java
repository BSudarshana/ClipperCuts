package lk.earth.earthuniversity.controller;

import lk.earth.earthuniversity.dao.AppointmentDao;
import lk.earth.earthuniversity.dao.CustomerDao;
import lk.earth.earthuniversity.entity.Appointment;
import lk.earth.earthuniversity.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private CustomerDao customerDao;

    /**
     * Retrieves a list of appointments with optional filtering by date, customer code or status ID.
     *
     * @param params Optional query parameters to filter appointments.
     * @return List of filtered appointments.
     */
    @GetMapping(produces = "application/json")
    public List<Appointment> get(@RequestParam HashMap<String, String> params) {

        List<Appointment> appointments = appointmentDao.findAll();

        if (params.isEmpty()) return appointments;

        String dateStr = params.get("date");
        String customerCode = params.get("customerCode");
        String statusIdStr = params.get("status");

        Stream<Appointment> stream = appointments.stream();

        if (dateStr != null)
            stream = stream.filter(a -> a.getAppointmentDate().toString().contains(dateStr));

        if (customerCode != null)
            stream = stream.filter(a -> a.getCustomer().getCode().contains(customerCode));

        if (statusIdStr != null) {
            int statusId = Integer.parseInt(statusIdStr);
            stream = stream.filter(a -> a.getAppointmentstatus().getId().equals(statusId));
        }

        return stream.collect(Collectors.toList());
    }

    /**
     * Adds a new appointment to the system.
     *
     * @param appointment Appointment object from the client.
     * @return Response with ID, URL, and any errors.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> add(@RequestBody Appointment appointment) {

        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        // Validation: Check if customer exists
        Optional<Customer> customerOpt = customerDao.findById(appointment.getCustomer().getId());
        if (!customerOpt.isPresent()) {
            errors += "<br> Invalid customer";
        }

        // Validation: Check for duplicate time slot for same customer
        List<Appointment> existing = appointmentDao.checkCustomerConflict(
                appointment.getCustomer().getId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime());

        if (!existing.isEmpty()) {
            errors += "<br> Duplicate appointment for same time";
        }

        if (errors.equals("")) {
            appointmentDao.save(appointment);
        } else {
            errors = "Server Validation Errors: <br>" + errors;
        }

        response.put("id", String.valueOf(appointment.getId()));
        response.put("url", "/appointments/" + appointment.getId());
        response.put("errors", errors);

        return response;
    }

    /**
     * Updates an existing appointment.
     *
     * @param appointment Appointment object from client with ID.
     * @return Response with status and any errors.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> update(@RequestBody Appointment appointment) {

        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        // Validate customer
        if (!customerDao.existsById(appointment.getCustomer().getId())) {
            errors += "<br> Invalid customer";
        }

        // Check for conflict (exclude current appointment ID)
        List<Appointment> conflicts = appointmentDao.checkCustomerConflict(
                appointment.getCustomer().getId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime());

        boolean conflictExists = conflicts.stream()
                .anyMatch(a -> !a.getId().equals(appointment.getId()));

        if (conflictExists) {
            errors += "<br> Another appointment already exists for the same time";
        }

        if (errors.equals("")) {
            appointmentDao.save(appointment);
        } else {
            errors = "Server Validation Errors: <br>" + errors;
        }

        response.put("id", String.valueOf(appointment.getId()));
        response.put("url", "/appointments/" + appointment.getId());
        response.put("errors", errors);

        return response;
    }

    /**
     * Deletes an appointment from the system.
     *
     * @param id Appointment ID
     * @return Deletion status and messages
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public HashMap<String, String> delete(@PathVariable Integer id) {

        HashMap<String, String> response = new HashMap<>();
        String errors = "";

        Optional<Appointment> appointmentOpt = appointmentDao.findById(id);

        if (!appointmentOpt.isPresent()) {
            errors += "<br> Appointment not found";
        }

        if (errors.equals("")) {
            appointmentDao.delete(appointmentOpt.get());
        } else {
            errors = "Server Validation Errors: <br>" + errors;
        }

        response.put("id", String.valueOf(id));
        response.put("url", "/appointments/" + id);
        response.put("errors", errors);

        return response;
    }
}