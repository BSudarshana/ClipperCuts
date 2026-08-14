package com.clippercuts.util;

import com.clippercuts.dao.CustomerDao;
import com.clippercuts.dao.GoodReceiveNoteDao;
import com.clippercuts.dao.InvoiceDao;
import com.clippercuts.dao.PurchaseorderDao;
import com.clippercuts.dao.StockwriteoffDao;
import com.clippercuts.dao.SupplierDao;
import com.clippercuts.entity.Customer;
import com.clippercuts.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.clippercuts.dao.ExpenseDao;

import java.time.Year;

@Service
public class NumberServiceImp implements NumberService {

    @Autowired
    private CustomerDao customerdao;

    @Autowired
    private SupplierDao supplierdao;

    @Autowired
    private InvoiceDao invoicedao;

    @Autowired
    private PurchaseorderDao purchaseorderDao;

    @Autowired
    private GoodReceiveNoteDao goodreceivenotedao;

    @Autowired
    private StockwriteoffDao stockwriteoffDao;

    @Autowired
    private ExpenseDao expenseDao;

    @Override
    public String generateCustomerCode() {
        Customer lastCustomer = customerdao.findTopByOrderByIdDesc();

        if (lastCustomer == null || lastCustomer.getCode() == null) {
            return "CUS0001";
        }

        String lastCode = lastCustomer.getCode();
        int number = Integer.parseInt(lastCode.substring(3)) + 1;
        return String.format("CUS%04d", number);
    }

    @Override
    public String generateSupplierCode() {
        Supplier lastSupplier = supplierdao.findTopByOrderByIdDesc();

        if (lastSupplier == null || lastSupplier.getRegisternumber() == null) {
            return "SUP0001";
        }

        String lastCode = lastSupplier.getRegisternumber();
        int number = Integer.parseInt(lastCode.substring(3)) + 1;
        return String.format("SUP%04d", number);
    }

    @Override
    public String getLastInvoiceByYear() {
        int currentYear = Year.now().getValue();
        String lastInvoice = invoicedao.getLastInvoiceByYear(currentYear);

        int nextNumber = 1;
        if (lastInvoice != null) {
            String[] parts = lastInvoice.split("-");
            nextNumber = Integer.parseInt(parts[2]) + 1;
        }

        return String.format("INV-%d-%06d", currentYear, nextNumber);
    }

    @Override
    public String generatePurchaseOrderNumber() {
        int currentYear = Year.now().getValue();
        String lastNumber = purchaseorderDao.getLastPurchaseOrderByYear(currentYear);

        int nextNumber = 1;
        if (lastNumber != null && !lastNumber.trim().isEmpty()) {
            String[] parts = lastNumber.split("-");
            if (parts.length == 3) {
                nextNumber = Integer.parseInt(parts[2]) + 1;
            }
        }

        return String.format("PO-%d-%06d", currentYear, nextNumber);
    }

    @Override
    public String generateGrnNumber() {
        int currentYear = Year.now().getValue();
        String lastNumber = goodreceivenotedao.getLastGrnByYear(currentYear);

        int nextNumber = 1;
        if (lastNumber != null && !lastNumber.trim().isEmpty()) {
            String[] parts = lastNumber.split("-");
            nextNumber = Integer.parseInt(parts[2]) + 1;
        }

        return String.format("GRN-%d-%04d", currentYear, nextNumber);
    }

    @Override
    public String generateStockWriteOffNumber() {
        int currentYear = Year.now().getValue();
        String lastNumber = stockwriteoffDao.getLastWriteOffNumberByYear(currentYear);

        int nextNumber = 1;
        if (lastNumber != null && !lastNumber.trim().isEmpty()) {
            String[] parts = lastNumber.split("-");
            nextNumber = Integer.parseInt(parts[2]) + 1;
        }

        return String.format("SWO-%d-%06d", currentYear, nextNumber);
    }

    @Override
    public String generateExpenseNumber() {
        int currentYear = Year.now().getValue();
        String lastNumber = expenseDao.getLastExpenseNumberByYear(currentYear);

        int nextNumber = 1;
        if (lastNumber != null && !lastNumber.trim().isEmpty()) {
            String[] parts = lastNumber.split("-");
            if (parts.length == 3) nextNumber = Integer.parseInt(parts[2]) + 1;
        }
        return String.format("EXP-%d-%06d", currentYear, nextNumber);
    }
}
