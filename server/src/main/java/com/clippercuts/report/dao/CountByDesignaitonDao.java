package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByDesignation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByDesignaitonDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByDesignation> countByDesignation() {
        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByDesignation(d.name, COUNT(e.fullname)) " +
                "FROM Employee e JOIN e.designation d " +
                "GROUP BY d.name",
                CountByDesignation.class
        ).getResultList();
    }
}