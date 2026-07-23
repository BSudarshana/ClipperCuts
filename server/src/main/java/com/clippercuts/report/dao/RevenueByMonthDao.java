package com.clippercuts.report.dao;

import com.clippercuts.report.entity.RevenueByMonth;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RevenueByMonthDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RevenueByMonth> revenueByMonth() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.RevenueByMonth(" +
                "YEAR(i.invoicedate), " +
                "MONTH(i.invoicedate), " +
                "SUM(i.final_amount)) " +
                "FROM Invoice i " +
                "GROUP BY YEAR(i.invoicedate), MONTH(i.invoicedate) " +
                "ORDER BY YEAR(i.invoicedate), MONTH(i.invoicedate)",
                RevenueByMonth.class
        ).getResultList();

    }
}