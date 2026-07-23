package com.clippercuts.util;

import com.clippercuts.dao.*;
import com.clippercuts.dto.AppointmentCreateRequest;
import com.clippercuts.dto.AppointmentLineRequest;
import com.clippercuts.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

@Service
public class AppointmentBookingService {
    private static final int INITIAL_APPOINTMENT_STATUS_ID = 1;
    private static final int PENDING_STATUS_ID = 1;
    private static final int ASSIGNED_STATUS_ID = 2;
    private static final int IN_PROGRESS_STATUS_ID = 3;

    private final AppointmentDao appointmentDao;
    private final AppointmentserviceDao lineDao;
    private final CustomerDao customerDao;
    private final com.clippercuts.dao.ServiceDao serviceDao;
    private final EmployeeDao employeeDao;
    private final ServiceHasEmployeeDao qualificationDao;
    private final AppointmentstatusDao appointmentstatusDao;
    private final AppointmentservicestatusDao lineStatusDao;

    public AppointmentBookingService(
            AppointmentDao appointmentDao,
            AppointmentserviceDao lineDao,
            CustomerDao customerDao,
            com.clippercuts.dao.ServiceDao serviceDao,
            EmployeeDao employeeDao,
            ServiceHasEmployeeDao qualificationDao,
            AppointmentstatusDao appointmentstatusDao,
            AppointmentservicestatusDao lineStatusDao) {
        this.appointmentDao = appointmentDao;
        this.lineDao = lineDao;
        this.customerDao = customerDao;
        this.serviceDao = serviceDao;
        this.employeeDao = employeeDao;
        this.qualificationDao = qualificationDao;
        this.appointmentstatusDao = appointmentstatusDao;
        this.lineStatusDao = lineStatusDao;
    }

    @Transactional
    public Appointment create(AppointmentCreateRequest request) {
        Customer customer = customerDao.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer"));

        if (!appointmentDao.checkCustomerConflict(
                customer.getId(), request.getAppointmentDate(), request.getAppointmentTime()).isEmpty()) {
            throw new IllegalArgumentException("Customer already has an appointment at this time");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setDescription(request.getDescription());
        appointment.setCustomer(customer);
        appointment.setAppointmentstatus(appointmentstatusDao.findById(INITIAL_APPOINTMENT_STATUS_ID)
                .orElseThrow(() -> new IllegalStateException("Initial appointment status is missing")));
        appointmentDao.save(appointment);

        LocalTime cursor = request.getAppointmentTime().toLocalTime();
        List<Appointmentservice> savedLines = new ArrayList<>();

        for (AppointmentLineRequest requestedLine : request.getServices()) {
            com.clippercuts.entity.Service salonService = serviceDao.findById(requestedLine.getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid service"));

            LocalTime end = cursor.plusMinutes(salonService.getDuration());
            Time startTime = Time.valueOf(cursor);
            Time endTime = Time.valueOf(end);
            Employee employee = null;
            int lineStatusId = PENDING_STATUS_ID;

            if (requestedLine.getEmployeeId() != null) {
                employee = employeeDao.findById(requestedLine.getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid employee"));
                validateAssignment(salonService.getId(), employee.getId(),
                        request.getAppointmentDate(), startTime, endTime);
                lineStatusId = ASSIGNED_STATUS_ID;
            }

            Appointmentservice line = new Appointmentservice();
            line.setAppointment(appointment);
            line.setService(salonService);
            line.setEmployee(employee);
            line.setStartTime(startTime);
            line.setEndTime(endTime);
            line.setAgreedPrice(salonService.getPrice());
            line.setAppointmentservicestatus(lineStatusDao.findById(lineStatusId)
                    .orElseThrow(() -> new IllegalStateException("Appointment service status is missing")));
            savedLines.add(lineDao.save(line));
            cursor = end;
        }

        appointment.setAppointmentservices(savedLines);
        return appointment;
    }

    @Transactional
    public HashMap<String, String> updateAppointment(
            Integer appointmentId,
            AppointmentCreateRequest request) {

        Appointment appointment = appointmentDao.findById(appointmentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Appointment not found"));

        Customer customer = customerDao.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid customer"));

        /*
         * Only Pending and Assigned services can be edited.
         * This prevents an In Progress or Completed service from being reset.
         */
        if (appointment.getAppointmentservices() != null) {
            for (Appointmentservice existingLine :
                    appointment.getAppointmentservices()) {

                Integer statusId =
                        existingLine.getAppointmentservicestatus().getId();

                if (!statusId.equals(PENDING_STATUS_ID) &&
                        !statusId.equals(ASSIGNED_STATUS_ID)) {

                    throw new IllegalArgumentException(
                            "This appointment cannot be changed because " +
                                    "one or more services have already started."
                    );
                }
            }
        }

        /*
         * Exclude the current appointment when checking whether the
         * customer already has another appointment at the new time.
         */
        if (!appointmentDao.checkCustomerConflictForUpdate(
                customer.getId(),
                request.getAppointmentDate(),
                request.getAppointmentTime(),
                appointmentId
        ).isEmpty()) {
            throw new IllegalArgumentException(
                    "Customer already has another appointment at this time"
            );
        }

        /*
         * Delete existing lines first. This also prevents the employee
         * availability check from detecting the old lines as conflicts.
         */


        List<Appointmentservice> existingLines = new ArrayList<>();

        if (appointment.getAppointmentservices() != null) {
            existingLines.addAll(appointment.getAppointmentservices());
        }

        // Remove old references from the managed Appointment first
            appointment.setAppointmentservices(new ArrayList<>());

        // Then delete the old database records
            if (!existingLines.isEmpty()) {
                lineDao.deleteAll(existingLines);
                lineDao.flush();
            }

        // Update the existing appointment, not a new Appointment object
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setDescription(request.getDescription());
        appointment.setCustomer(customer);


        LocalTime cursor = request.getAppointmentTime().toLocalTime();
        List<Appointmentservice> updatedLines = new ArrayList<>();

        for (AppointmentLineRequest requestedLine : request.getServices()) {

            com.clippercuts.entity.Service salonService =
                    serviceDao.findById(requestedLine.getServiceId())
                            .orElseThrow(() ->
                                    new IllegalArgumentException(
                                            "Invalid service"
                                    ));

            LocalTime end =
                    cursor.plusMinutes(salonService.getDuration());

            Time startTime = Time.valueOf(cursor);
            Time endTime = Time.valueOf(end);

            Employee employee = null;
            int lineStatusId = PENDING_STATUS_ID;

            if (requestedLine.getEmployeeId() != null) {
                employee = employeeDao
                        .findById(requestedLine.getEmployeeId())
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid employee"
                                ));

                validateAssignment(
                        salonService.getId(),
                        employee.getId(),
                        request.getAppointmentDate(),
                        startTime,
                        endTime
                );

                lineStatusId = ASSIGNED_STATUS_ID;
            }

            Appointmentservicestatus lineStatus = lineStatusDao.findById(lineStatusId)
                            .orElseThrow(() -> new IllegalStateException(
                                            "Appointment service status is missing"
                                    ));

            Appointmentservice line = new Appointmentservice();

            line.setAppointment(appointment);
            line.setService(salonService);
            line.setEmployee(employee);
            line.setStartTime(startTime);
            line.setEndTime(endTime);
            line.setAgreedPrice(salonService.getPrice());
            line.setAppointmentservicestatus(lineStatus);

            updatedLines.add(lineDao.save(line));

            cursor = end;
        }

        appointment.setAppointmentservices(updatedLines);

        HashMap<String, String> response = new HashMap<>();

        response.put("id", String.valueOf(appointment.getId()));
        response.put(
                "url",
                "/appointments/" + appointment.getId()
        );
        response.put("errors", "");

        return response;
    }

    @Transactional(readOnly = true)
    public List<Employee> availableEmployees(Integer serviceId, java.sql.Date date, Time startTime) {
        com.clippercuts.entity.Service salonService = serviceDao.findById(serviceId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid service"));
        Time endTime = Time.valueOf(startTime.toLocalTime().plusMinutes(salonService.getDuration()));
        List<Employee> qualified = qualificationDao.findEmployeesByServiceId(serviceId);
        qualified.removeIf(employee -> !lineDao.findEmployeeConflicts(
                employee.getId(), date, startTime, endTime).isEmpty());
        return qualified;
    }

    @Transactional
    public Appointmentservice assignToEmployee(Integer lineId, Employee employee) {
        Appointmentservice line = lineDao.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment service not found"));
        if (line.getEmployee() != null ||
                !line.getAppointmentservicestatus().getId().equals(PENDING_STATUS_ID)) {
            throw new IllegalArgumentException("This service is no longer pending");
        }
        validateAssignment(line.getService().getId(), employee.getId(),
                line.getAppointment().getAppointmentDate(), line.getStartTime(), line.getEndTime());
        line.setEmployee(employee);
        line.setAppointmentservicestatus(lineStatusDao.findById(ASSIGNED_STATUS_ID)
                .orElseThrow(() -> new IllegalStateException("Assigned status is missing")));
        return lineDao.save(line);
    }

    @Transactional
    public Appointmentservice start(Integer lineId, Employee employee) {
        Appointmentservice line = lineDao.findById(lineId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment service not found"));
        if (line.getEmployee() == null || !line.getEmployee().getId().equals(employee.getId())) {
            throw new IllegalArgumentException("This service is not assigned to you");
        }
        if (!line.getAppointmentservicestatus().getId().equals(ASSIGNED_STATUS_ID)) {
            throw new IllegalArgumentException("Only an assigned service can be started");
        }
        line.setAppointmentservicestatus(lineStatusDao.findById(IN_PROGRESS_STATUS_ID)
                .orElseThrow(() -> new IllegalStateException("In Progress status is missing")));
        return lineDao.save(line);
    }

    private void validateAssignment(Integer serviceId, Integer employeeId,
                                    java.sql.Date date, Time start, Time end) {
        if (!qualificationDao.existsByService_IdAndEmployee_Id(serviceId, employeeId)) {
            throw new IllegalArgumentException("Employee is not qualified for the selected service");
        }
        if (!lineDao.findEmployeeConflicts(employeeId, date, start, end).isEmpty()) {
            throw new IllegalArgumentException("Employee is not available for the selected time");
        }
    }
}
