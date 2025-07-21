package com.clippercuts.dao;

import com.clippercuts.entity.GoodReceiveNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GoodReceiveNoteDao extends JpaRepository<GoodReceiveNote,Integer> {
    @Query("select g from GoodReceiveNote g where g.id = :id")
    GoodReceiveNote findByGrnId(@Param("id") Integer id);

    @Query("select g from GoodReceiveNote g where g.grnNumber = :name")
    GoodReceiveNote findByGrnNumber(String name);

}

