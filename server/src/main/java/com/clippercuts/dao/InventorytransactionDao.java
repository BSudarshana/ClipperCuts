package com.clippercuts.dao;

import com.clippercuts.entity.Inventorytransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventorytransactionDao
        extends JpaRepository<Inventorytransaction, Integer> {
}