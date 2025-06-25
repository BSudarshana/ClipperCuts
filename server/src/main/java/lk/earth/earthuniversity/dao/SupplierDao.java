package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Customer;
import lk.earth.earthuniversity.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierDao extends JpaRepository<Supplier,Integer> {
//    Supplier findRegNumber(String number);
//    Supplier findByMobile(String mobile);

    @Query("select s from Supplier s where s.id = :id")
    Supplier findByMyId(@Param("id") Integer id);

}

