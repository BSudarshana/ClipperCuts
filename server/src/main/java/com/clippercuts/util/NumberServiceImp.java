package com.clippercuts.util;

import com.clippercuts.dao.CustomerDao;
import com.clippercuts.dao.SupplierDao;
import com.clippercuts.entity.Customer;
import com.clippercuts.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberServiceImp implements NumberService {

    @Autowired
    private CustomerDao customerdao;

    @Autowired
    private SupplierDao supplierdao;

    @Override
    public String generateCustomerCode() {
        Customer lastCustomer = customerdao.findTopByOrderByIdDesc();

        if (lastCustomer == null || lastCustomer.getCode() == null) {
            return "CUS0001";
        }

        String lastCode = lastCustomer.getCode(); // Example: CUS0007

        int number = Integer.parseInt(lastCode.substring(3));

        number++;

        return String.format("CUS%04d", number);
    }

    @Override
    public String generateSupplierCode() {
        Supplier lastSupplier = supplierdao.findTopByOrderByIdDesc();

        if (lastSupplier == null || lastSupplier.getRegisternumber() == null) {
            return "SUP0001";
        }
        String lastCode = lastSupplier.getRegisternumber();
        int number = Integer.parseInt(lastCode.substring(3));
        number++;

        return String.format("SUP%04d", number);
    }
}
