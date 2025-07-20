package com.clippercuts.dao;

import com.clippercuts.entity.Paymentstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusDao extends JpaRepository<Paymentstatus,Integer> {

}

