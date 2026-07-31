package com.clippercuts.dao;

import com.clippercuts.entity.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentDao extends JpaRepository<Payment,Integer> {

//    @Override
//    @EntityGraph(attributePaths = {
//            "invoice",
//            "invoice.paymentstatus",
//            "invoice.promotion",
//            "invoice.promotion.promotionstatus",
//            "invoice.appointment",
//            "invoice.appointment.appointmentstatus",
//            "invoice.appointment.customer",
//            "invoice.appointment.customer.gender",
//            "invoice.appointment.customer.customertype",
//            "invoice.appointment.customer.customerstatus",
//            "paymentmethod"
//    })
//    List<Payment> findAll();
//
//    @EntityGraph(attributePaths = {
//            "invoice",
//            "invoice.paymentstatus",
//            "invoice.promotion",
//            "invoice.promotion.promotionstatus",
//            "invoice.appointment",
//            "invoice.appointment.appointmentstatus",
//            "invoice.appointment.customer",
//            "invoice.appointment.customer.gender",
//            "invoice.appointment.customer.customertype",
//            "invoice.appointment.customer.customerstatus",
//
//            "invoice.appointment.appointmentservices",
//            "invoice.appointment.appointmentservices.appointmentservicestatus",
//            "invoice.appointment.appointmentservices.service",
//            "invoice.appointment.appointmentservices.service.servicestatus",
//            "invoice.appointment.appointmentservices.service.servicecategory",
//            "invoice.appointment.appointmentservices.employee",
//
//            "paymentmethod"
//    })
//    @Query("select p from Payment p where p.id = :id")
//    Optional<Payment> findDetailedById(@Param("id") Integer id);

    @Override
    List<Payment> findAll();

    @Query("select p from Payment p where p.id = :id")
    Optional<Payment> findDetailedById(@Param("id") Integer id);

    Payment findByReceiptnumber(String receiptnumber);

    boolean existsByInvoice_Id(Integer invoiceId);

    @Query(value =
            "SELECT receiptnumber FROM payment " +
                    "WHERE receiptnumber LIKE CONCAT('REC-', :year, '-%') " +
                    "ORDER BY id DESC LIMIT 1",
            nativeQuery = true)
    String getLastReceiptByYear(@Param("year") int year);

}

