package com.clippercuts.dao;

import com.clippercuts.entity.Invoice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

    Invoice findByInvoicenumber(String invoicenumber);

    boolean existsByAppointment_Id(Integer appointmentId);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "appointment",
                    "appointment.customer",
                    "appointment.appointmentstatus",
                    "appointment.appointmentservices",
                    "appointment.appointmentservices.service",
                    "appointment.appointmentservices.employee",
                    "paymentstatus",
                    "promotion"
            }
    )
    @Query("select i from Invoice i where i.id = :id")
    Optional<Invoice> findDetailedById(@Param("id") Integer id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "appointment",
                    "appointment.customer",
                    "paymentstatus",
                    "promotion"
            }
    )
    List<Invoice> findByPaymentstatus_NameIgnoreCase(String status);

//    @EntityGraph(attributePaths = {"appointment", "appointment.customer", "paymentstatus", "promotion"})
//    List<Invoice> findAll();

    @Override
    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "appointment",
                    "appointment.customer",
                    "appointment.appointmentstatus",
                    "paymentstatus",
                    "promotion",
                    "createdByUser"
            }
    )
    List<Invoice> findAll();

    @Query(value =
            "SELECT invoicenumber FROM invoice " +
                    "WHERE invoicenumber LIKE CONCAT('INV-', :year, '-%') " +
                    "ORDER BY id DESC LIMIT 1",
            nativeQuery = true)
    String getLastInvoiceByYear(@Param("year") int year);

    @EntityGraph(attributePaths = {"createdByUser", "appointment", "appointment.customer", "paymentstatus", "promotion"})
    List<Invoice> findByCreatedByUser_Id(Integer userId);

    @EntityGraph(attributePaths = {"createdByUser", "appointment", "appointment.customer", "paymentstatus", "promotion"})
    List<Invoice> findByCreatedByUser_Username(String username);

    List<Invoice>
    findByCreatedByUser_IdAndInvoicedateBetween(
            Integer userId,
            Timestamp start,
            Timestamp end
    );

}

