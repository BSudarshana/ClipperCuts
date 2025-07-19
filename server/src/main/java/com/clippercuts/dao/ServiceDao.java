package com.clippercuts.dao;

import com.clippercuts.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ServiceDao extends JpaRepository<Service,Integer> {


    @Query("select s from Service s where s.id = :id")
    Service findByServiceId(@Param("id") Integer id);

    @Query("select s from Service s where s.name = :name")
    Service findByServiceName(String name);

    @Query("select s from Service s where s.code = :code")
    Service findByServiceCode(String code);

}

