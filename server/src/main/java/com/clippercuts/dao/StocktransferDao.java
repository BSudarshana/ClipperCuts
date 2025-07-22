package com.clippercuts.dao;

import com.clippercuts.entity.Stocktransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StocktransferDao extends JpaRepository<Stocktransfer,Integer> {


    @Query("select s from Stocktransfer s where s.id = :id")
    Stocktransfer findByMyId(@Param("id") Integer id);

}

