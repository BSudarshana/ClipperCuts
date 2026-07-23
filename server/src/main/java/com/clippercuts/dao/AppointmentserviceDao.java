package com.clippercuts.dao;

import com.clippercuts.entity.Appointmentservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface AppointmentserviceDao extends JpaRepository<Appointmentservice,Integer> {
    @Query("SELECT aps FROM Appointmentservice aps " +
            "WHERE aps.employee.id = :employeeId " +
            "AND aps.appointment.appointmentDate = :date " +
            "AND aps.appointmentservicestatus.id <> 5 " +
            "AND aps.startTime < :requestedEnd " +
            "AND aps.endTime > :requestedStart")
    List<Appointmentservice> findEmployeeConflicts(
            @Param("employeeId") Integer employeeId,
            @Param("date") Date date,
            @Param("requestedStart") Time requestedStart,
            @Param("requestedEnd") Time requestedEnd);

    @Query("SELECT aps FROM Appointmentservice aps " +
            "WHERE aps.employee.id = :employeeId " +
            "AND aps.appointment.appointmentDate >= CURRENT_DATE " +
            "AND aps.appointmentservicestatus.id IN (2, 3) " +
            "ORDER BY aps.appointment.appointmentDate, aps.startTime")
    List<Appointmentservice> findAssignedUpcoming(@Param("employeeId") Integer employeeId);

    @Query("SELECT aps FROM Appointmentservice aps " +
            "WHERE aps.employee IS NULL " +
            "AND aps.appointment.appointmentDate >= CURRENT_DATE " +
            "AND aps.appointmentservicestatus.id = 1 " +
            "ORDER BY aps.appointment.appointmentDate, aps.startTime")
    List<Appointmentservice> findPendingUpcoming();
}

