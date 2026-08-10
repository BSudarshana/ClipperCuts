package com.clippercuts.dao;

import com.clippercuts.entity.Location;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryLocationDao extends JpaRepository<Location, Integer> {
    @Override
    @EntityGraph(attributePaths = {"locationtype"})
    List<Location> findAll();
}
