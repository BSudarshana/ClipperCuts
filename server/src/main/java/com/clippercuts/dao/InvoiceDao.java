package com.clippercuts.dao;

import com.clippercuts.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceDao extends JpaRepository<Invoice,Integer> {


    @Query("select i from Invoice i where i.id = :id")
    Invoice findInvoiceById(@Param("id") Integer id);

//    @Query("select s from Service s where s.name = :name")
//    Service findByServiceName(String name);

    @Query("select i from Invoice i where i.invoicenumber = :invoicenumber")
    Invoice findByInvoiceNumber(String invoicenumber);

}

