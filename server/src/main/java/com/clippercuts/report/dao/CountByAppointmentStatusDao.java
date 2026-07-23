package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByAppointmentStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByAppointmentStatusDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByAppointmentStatus> countByAppointmentStatus() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByAppointmentStatus(" +
                "s.name, COUNT(a.id)) " +
                "FROM Appointment a, Appointmentstatus s " +
                "WHERE a.appointmentstatus.id = s.id " +
                "GROUP BY s.id",
                CountByAppointmentStatus.class
        ).getResultList();

    }
}