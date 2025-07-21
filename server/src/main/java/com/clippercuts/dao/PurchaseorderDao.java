package com.clippercuts.dao;

import com.clippercuts.entity.Purchaseorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PurchaseorderDao extends JpaRepository<Purchaseorder,Integer> {
    @Query("select p from Purchaseorder p where p.id = :id")
    Purchaseorder findPOById(@Param("id") Integer id);

    @Query("select p from Purchaseorder p where p.poNumber = :poNumber")
    Purchaseorder findByPONumber(String poNumber);
}

