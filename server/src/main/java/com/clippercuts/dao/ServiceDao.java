package com.clippercuts.dao;

import com.clippercuts.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clippercuts.entity.Employee;
import java.util.List;

public interface ServiceDao extends JpaRepository<Service,Integer> {

//    @Override
//    @EntityGraph(attributePaths = {
//            "serviceHasEmployees",
//            "serviceHasEmployees.employee"
//    })

    java.util.List<Service> findAll();

    @Query("select s from Service s where s.id = :id")
    Service findByServiceId(@Param("id") Integer id);

    @Query("select s from Service s where s.name = :name")
    Service findByServiceName(String name);

    @Query("select s from Service s where s.code = :code")
    Service findByServiceCode(String code);

    @Query("select distinct she.employee " +
            "from ServiceHasEmployee she " +
            "where she.service.id = :serviceId " +
            "order by she.employee.callingname")
    List<Employee> findEmployeesByServiceId(
            @Param("serviceId") Integer serviceId
    );

}

