package com.clippercuts.report.dao;

import com.clippercuts.report.entity.TotalByPoSupplier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TotalByPoSupplierDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TotalByPoSupplier> totalByPoSupplier() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.TotalByPoSupplier(" +
                "s.name, SUM(po.total_amount)) " +
                "FROM Purchaseorder po, Supplier s " +
                "WHERE po.supplier.id = s.id " +
                "GROUP BY s.id",
                TotalByPoSupplier.class
        ).getResultList();

    }
}