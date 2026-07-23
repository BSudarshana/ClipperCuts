package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByEmpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByEmpStatusDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByEmpStatus> countByEmpStatus() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByEmpStatus(" +
                "s.name, COUNT(e.id)) " +
                "FROM Employee e, Empstatus s " +
                "WHERE e.empstatus.id = s.id " +
                "GROUP BY s.id",
                CountByEmpStatus.class
        ).getResultList();

    }
}