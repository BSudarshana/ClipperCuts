package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByGender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByGenderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByGender> countByGender() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByGender(" +
                "g.name, COUNT(c.id)) " +
                "FROM Customer c, Gender g " +
                "WHERE c.gender.id = g.id " +
                "GROUP BY g.id",
                CountByGender.class
        ).getResultList();

    }
}