package com.clippercuts.dao;

import com.clippercuts.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceDao extends JpaRepository<Invoice,Integer> {


    @Query("select i from Invoice i where i.id = :id")
    Invoice findInvoiceById(@Param("id") Integer id);

    @Query("select i from Invoice i where i.invoicenumber = :invoicenumber")
    Invoice findByInvoiceNumber(String invoicenumber);

    @Query(value =
            "SELECT invoice_no " +
                    "FROM invoice " +
                    "WHERE invoice_no LIKE CONCAT('INV-', :year, '-%') " +
                    "ORDER BY invoice_id DESC " +
                    "LIMIT 1",
            nativeQuery = true)
    String getLastInvoiceByYear(@Param("year") int year);

}

