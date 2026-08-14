package com.clippercuts.dao;

import com.clippercuts.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemDao extends JpaRepository<InvoiceItem, Integer> {
}
