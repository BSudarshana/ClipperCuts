package com.clippercuts.dao;

import com.clippercuts.entity.Purchaseorder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseorderDao extends JpaRepository<Purchaseorder,Integer> {

}

