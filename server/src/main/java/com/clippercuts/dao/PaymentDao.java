package com.clippercuts.dao;

import com.clippercuts.entity.Payment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.*;

public interface PaymentDao extends JpaRepository<Payment, Integer> {
    @Override
    @EntityGraph(attributePaths = { "invoice", "invoice.paymentstatus", "invoice.customer", "invoice.appointment",
            "invoice.appointment.customer", "paymentmethod", "receivedByUser" })
    List<Payment> findAll();

    @EntityGraph(attributePaths = { "invoice", "invoice.paymentstatus", "invoice.customer", "invoice.appointment",
            "invoice.appointment.customer", "paymentmethod", "receivedByUser" })
    @Query("select p from Payment p where p.id=:id")
    Optional<Payment> findDetailedById(@Param("id") Integer id);

    @Query("select coalesce(sum(p.amount),0) from Payment p where p.invoice.id=:invoiceId")
    BigDecimal totalPaid(@Param("invoiceId") Integer invoiceId);

    Payment findByReceiptnumber(String n);

    @Query(value = "SELECT receiptnumber FROM payment WHERE receiptnumber LIKE CONCAT('REC-',:year,'-%') " +
            "ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLastReceiptByYear(@Param("year") int year);
}
