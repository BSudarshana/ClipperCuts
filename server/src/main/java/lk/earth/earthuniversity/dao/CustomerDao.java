package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer,Integer> {

    Customer findByCode(String number);
    Customer findByMobile(String mobile);

    @Query("select c from Customer c where c.id = :id")
    Customer findByMyId(@Param("id") Integer id);

    @Query("SELECT NEW Customer (c.id, c.fullname) FROM Customer c")
    List<Customer> findAllNameId();



}

