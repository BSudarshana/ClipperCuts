package com.clippercuts.dao;

import com.clippercuts.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionDao extends JpaRepository<Question,Integer> {

    @Query("select q from Question q where q.id = :id")
    Question findQuestionById(@Param("id") Integer id);

    @Query("select q from Question q where q.id = :text")
    Question findQuestionByText(@Param("id") String text);

}

