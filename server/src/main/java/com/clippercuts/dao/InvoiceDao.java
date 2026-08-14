package com.clippercuts.dao;

import com.clippercuts.entity.Invoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import javax.persistence.LockModeType;
import java.sql.Timestamp;
import java.util.*;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {
    Invoice findByInvoicenumber(String n);

    boolean existsByAppointment_Id(Integer id);

    @Override
    @EntityGraph(attributePaths = { "appointment", "appointment.customer", "appointment.appointmentstatus",
            "paymentstatus", "promotion", "customer", "createdByUser", "invoiceItems", "invoiceItems.item",
            "invoiceItems.location" })
    List<Invoice> findAll();

    @EntityGraph(attributePaths = { "appointment", "appointment.customer", "appointment.appointmentstatus",
            "appointment.appointmentservices", "appointment.appointmentservices.service",
            "appointment.appointmentservices.employee", "paymentstatus", "promotion", "customer", "createdByUser",
            "invoiceItems", "invoiceItems.item", "invoiceItems.location" })
    @Query("select distinct i from Invoice i where i.id=:id")
    Optional<Invoice> findDetailedById(@Param("id") Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = { "paymentstatus", "customer", "appointment", "appointment.customer" })
    @Query("select i from Invoice i where i.id=:id")
    Optional<Invoice> findForPaymentUpdate(@Param("id") Integer id);

    @EntityGraph(attributePaths = { "appointment", "appointment.customer", "paymentstatus", "promotion", "customer" })
    List<Invoice> findByPaymentstatus_NameIn(Collection<String> names);

    @Query(value = "SELECT invoicenumber FROM invoice WHERE invoicenumber LIKE CONCAT('INV-',:year,'-%') ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String getLastInvoiceByYear(@Param("year") int year);

    @EntityGraph(attributePaths = { "createdByUser", "appointment", "appointment.customer", "paymentstatus",
            "promotion", "customer" })
    List<Invoice> findByCreatedByUser_Id(Integer id);

    @EntityGraph(attributePaths = { "createdByUser", "appointment", "appointment.customer", "paymentstatus",
            "promotion", "customer" })
    List<Invoice> findByCreatedByUser_Username(String n);

    List<Invoice> findByCreatedByUser_IdAndInvoicedateBetween(Integer id, Timestamp a, Timestamp b);
}
