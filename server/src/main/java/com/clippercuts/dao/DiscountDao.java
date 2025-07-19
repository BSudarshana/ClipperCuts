package com.clippercuts.dao;

import com.clippercuts.entity.Discounttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountDao extends JpaRepository<Discounttype,Integer> {


    @Query("select dt from Discounttype dt where dt.id = :id")
    Discounttype findByDiscountId(@Param("id") Integer id);



}

