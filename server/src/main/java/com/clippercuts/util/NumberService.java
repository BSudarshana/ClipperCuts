package com.clippercuts.util;

public interface NumberService {
    String generateCustomerCode();
    String generateSupplierCode();
    String getLastInvoiceByYear();
    String generatePurchaseOrderNumber();
}