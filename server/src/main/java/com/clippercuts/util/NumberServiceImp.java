package com.clippercuts.util;

import com.clippercuts.dao.CustomerDao;
import com.clippercuts.dao.InvoiceDao;
import com.clippercuts.dao.SupplierDao;
import com.clippercuts.entity.Customer;
import com.clippercuts.entity.Invoice;
import com.clippercuts.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Calendar;

@Service
public class NumberServiceImp implements NumberService {

    @Autowired
    private CustomerDao customerdao;

    @Autowired
    private SupplierDao supplierdao;

    @Autowired
    private InvoiceDao invoicedao;

    @Override
    public String generateCustomerCode() {
        Customer lastCustomer = customerdao.findTopByOrderByIdDesc();

        if (lastCustomer == null || lastCustomer.getCode() == null) {
            return "CUS0001";
        }

        String lastCode = lastCustomer.getCode();

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

    @Override
    public String getLastInvoiceByYear() {
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentYear = java.time.Year.now().getValue();
        String lastInvoice = invoicedao.getLastInvoiceByYear(currentYear);

        int nextNumber = 1;
        if (lastInvoice != null) {
            String[] parts = lastInvoice.split("-");
            nextNumber = Integer.parseInt(parts[2]) + 1;
        }

        return String.format("INV-%d-%06d", currentYear, nextNumber);
    }
}
