package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Customer;
import lk.earth.earthuniversity.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDao extends JpaRepository<Supplier,Integer> {
//    Supplier findRegNumber(String number);
//    Customer findByMobile(String mobile);
}

