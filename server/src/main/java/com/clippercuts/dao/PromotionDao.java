package com.clippercuts.dao;

import com.clippercuts.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PromotionDao extends JpaRepository<Promotion,Integer> {


    @Query("select p from Promotion p where p.id = :id")
    Promotion findByPromotionId(@Param("id") Integer id);

    @Query("select p from Promotion p where p.title = :title")
    Promotion findByPromotionTitle(String title);



}

