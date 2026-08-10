package com.clippercuts.dao;

import com.clippercuts.entity.Stocktransfer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StocktransferDao extends JpaRepository<Stocktransfer,Integer> {


    @Query("select s from Stocktransfer s where s.id = :id")
    Stocktransfer findByMyId(@Param("id") Integer id);

    @Override
    @EntityGraph(attributePaths = {
            "locationfrom",
            "locationto",
            "employee",
            "createdByUser",
            "transferitems",
            "transferitems.item"
    })
    List<Stocktransfer> findAll();

    @EntityGraph(attributePaths = {
            "locationfrom",
            "locationto",
            "employee",
            "createdByUser",
            "transferitems",
            "transferitems.item"
    })
    @Query("select s from Stocktransfer s where s.id = :id")
    Optional<Stocktransfer> findDetailedById(
            @Param("id") Integer id
    );

}

