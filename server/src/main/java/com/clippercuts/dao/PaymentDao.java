package com.clippercuts.dao;

import com.clippercuts.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;

public interface PaymentDao extends JpaRepository<Payment,Integer> {


    @Query("select p from Payment p where p.id = :id")
    Payment findPaymentById(@Param("id") Integer id);

    @Query("select p from Payment p where p.paymentDate = :date")
    Payment findBydate(@Param("id") String date);

    @Query("select p from Payment p where p.receiptnumber = :receiptnumber")
    Payment findByReceiptnumber(@Param("id") String receiptnumber);

}

