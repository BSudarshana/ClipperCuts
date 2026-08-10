package com.clippercuts.dao;

import com.clippercuts.entity.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemDao extends JpaRepository<Item, Integer> {
    @Override
    @EntityGraph(attributePaths = {
            "itemstatus", "unittype", "itembrand", "subcategory", "subcategory.category"
    })
    List<Item> findAll();
}
