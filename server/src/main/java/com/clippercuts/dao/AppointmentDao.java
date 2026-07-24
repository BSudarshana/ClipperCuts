package com.clippercuts.dao;

import com.clippercuts.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface AppointmentDao extends JpaRepository<Appointment,Integer> {

    // 🔍 Find all appointments by a specific date
    List<Appointment> findByAppointmentDate(Date appointmentDate);

    // 🔍 Find appointments by customer code
    @Query("SELECT a FROM Appointment a WHERE a.customer.code = :code")
    List<Appointment> findByCustomerCode(@Param("code") String code);

    // 🔍 Find appointments by appointment status ID
    List<Appointment> findByAppointmentstatus_Id(Integer statusId);

    // 🔍 Find appointment by date and time
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = :date AND a.appointmentTime = :time")
    Appointment findAppointmentByDateTime(@Param("date") Date date, @Param("time") Time time);

    // 🔍 Find appointments by customer's mobile number
    @Query("SELECT a FROM Appointment a WHERE a.customer.mobile = :mobile")
    List<Appointment> findByCustomerMobile(@Param("mobile") String mobile);

    // 🔍 Find upcoming appointments after a given date
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate > :date ORDER BY a.appointmentDate ASC, a.appointmentTime ASC")
    List<Appointment> findUpcomingAppointments(@Param("date") Date date);

    // 🔍 Find today's appointments
    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = CURRENT_DATE")
    List<Appointment> findTodayAppointments();

    // 🔍 Check for conflicting appointments for a customer
    @Query("SELECT a FROM Appointment a WHERE a.customer.id = :customerId AND a.appointmentDate = :date AND a.appointmentTime = :time")
    List<Appointment> checkCustomerConflict(@Param("customerId") Integer customerId,
                                            @Param("date") Date date,
                                            @Param("time") Time time);



    @Override
    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
            "appointmentservices",
            "appointmentservices.service",
            "appointmentservices.employee",
            "appointmentservices.appointmentservicestatus"
    })
    Optional<Appointment> findById(Integer id);


    @Query("SELECT a FROM Appointment a " +
            "WHERE a.customer.id = :customerId " +
            "AND a.appointmentDate = :date " +
            "AND a.appointmentTime = :time " +
            "AND a.id <> :appointmentId")
    List<Appointment> checkCustomerConflictForUpdate(
            @Param("customerId") Integer customerId,
            @Param("date") Date date,
            @Param("time") Time time,
            @Param("appointmentId") Integer appointmentId
    );

    @EntityGraph(attributePaths = {
            "customer",
            "appointmentstatus",
            "appointmentservices",
            "appointmentservices.service",
            "appointmentservices.employee",
            "appointmentservices.appointmentservicestatus"
    })
    List<Appointment> findByAppointmentstatus_NameIgnoreCase(String statusName);

    @EntityGraph(attributePaths = {
            "customer",
            "appointmentstatus",
            "appointmentservices",
            "appointmentservices.service",
            "appointmentservices.employee",
            "appointmentservices.appointmentservicestatus"
    })
    @Query("SELECT DISTINCT a FROM Appointment a " +
            "WHERE LOWER(a.appointmentstatus.name) = LOWER(:statusName)")
    List<Appointment> findByStatusName(
            @Param("statusName") String statusName
    );

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "customer",
                    "appointmentstatus",
                    "appointmentservices",
                    "appointmentservices.service",
                    "appointmentservices.employee",
                    "appointmentservices.appointmentservicestatus"
            }
    )
    @Query("SELECT DISTINCT a FROM Appointment a " +
            "WHERE LOWER(a.appointmentstatus.name) = LOWER(:statusName) " +
            "AND NOT EXISTS (" +
            "    SELECT i.id FROM Invoice i " +
            "    WHERE i.appointment.id = a.id" +
            ")")
    List<Appointment> findEligibleForInvoice(
            @Param("statusName") String statusName
    );

}

