package com.clippercuts.dao;

import com.clippercuts.entity.ItemstockLocation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryDao extends JpaRepository<ItemstockLocation, Integer> {

    @Override
    @EntityGraph(attributePaths = {
            "item",
            "item.itemstatus",
            "item.unittype",
            "item.itembrand",
            "item.subcategory",
            "item.subcategory.category",
            "location",
            "location.locationtype"
    })
    List<ItemstockLocation> findAll();

}
