package com.clippercuts.dao;

import com.clippercuts.entity.Purchaseorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseorderDao extends JpaRepository<Purchaseorder,Integer> {
    @Override
    @EntityGraph(attributePaths = {
            "postatus",
            "supplier",
            "supplier.supplierstate",
            "supplier.supplierstype",
            "employee",
            "employee.gender",
            "employee.emptype",
            "employee.designation",
            "employee.empstatus",
            "poitems",
            "poitems.item",
            "poitems.item.itemstatus",
            "poitems.item.unittype",
            "poitems.item.itembrand",
            "poitems.item.subcategory",
            "poitems.item.subcategory.category"
    })
    java.util.List<Purchaseorder> findAll();

    @EntityGraph(attributePaths = {
            "postatus",
            "supplier",
            "supplier.supplierstate",
            "supplier.supplierstype",
            "employee",
            "employee.gender",
            "employee.emptype",
            "employee.designation",
            "employee.empstatus",
            "poitems",
            "poitems.item",
            "poitems.item.itemstatus",
            "poitems.item.unittype",
            "poitems.item.itembrand",
            "poitems.item.subcategory",
            "poitems.item.subcategory.category"
    })
    @Query("select p from Purchaseorder p where p.id = :id")
    Purchaseorder findPOById(@Param("id") Integer id);

    @Query("select p from Purchaseorder p where p.poNumber = :poNumber")
    Purchaseorder findByPONumber(String poNumber);

    @Query(value = "SELECT po_number FROM purchaseorder " +
            "WHERE po_number LIKE CONCAT('PO-', :year, '-%') " +
            "ORDER BY CAST(SUBSTRING_INDEX(po_number, '-', -1) AS UNSIGNED) DESC " +
            "LIMIT 1", nativeQuery = true)
    String getLastPurchaseOrderByYear(@Param("year") Integer year);
}

