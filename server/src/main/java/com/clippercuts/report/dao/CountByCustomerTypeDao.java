package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByCustomerType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByCustomerTypeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByCustomerType> countByCustomerType() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByCustomerType(" +
                "t.name, COUNT(c.id)) " +
                "FROM Customer c, Customertype t " +
                "WHERE c.customertype.id = t.id " +
                "GROUP BY t.id",
                CountByCustomerType.class
        ).getResultList();

    }
}