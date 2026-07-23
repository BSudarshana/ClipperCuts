package com.clippercuts.dao;

import com.clippercuts.entity.ServiceHasEmployee;
import com.clippercuts.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceHasEmployeeDao extends JpaRepository<ServiceHasEmployee, Integer> {
    boolean existsByService_IdAndEmployee_Id(Integer serviceId, Integer employeeId);

    @Query("SELECT she.employee FROM ServiceHasEmployee she WHERE she.service.id = :serviceId")
    List<Employee> findEmployeesByServiceId(@Param("serviceId") Integer serviceId);
}
