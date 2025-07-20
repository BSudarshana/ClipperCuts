package com.clippercuts.dao;

import com.clippercuts.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface DiscountDao extends JpaRepository<Discount,Integer> {


    @Query("select d from Discount d where d.id = :id")
    Discount findByDiscountById(@Param("id") Integer id);

    @Query("select d from Discount d where d.value = :value")
    Discount findByDiscountValue(@Param("id") BigDecimal value);



}

