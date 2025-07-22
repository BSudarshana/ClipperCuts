package com.clippercuts.dao;

import com.clippercuts.entity.Customerfeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerfeedbackDao extends JpaRepository<Customerfeedback,Integer> {

    @Query("select c from Customerfeedback c where c.id = :id")
    Customerfeedback findByfeedbackId(@Param("id") Integer id);

}

