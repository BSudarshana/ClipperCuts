package com.clippercuts.report.dao;

import com.clippercuts.report.entity.RevenueByPaymentMethod;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RevenueByPaymentMethodDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RevenueByPaymentMethod> revenueByPaymentMethod() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.RevenueByPaymentMethod(" +
                "pm.name, SUM(p.amount)) " +
                "FROM Payment p, Paymentmethod pm " +
                "WHERE p.paymentmethod.id = pm.id " +
                "GROUP BY pm.id",
                RevenueByPaymentMethod.class
        ).getResultList();

    }
}