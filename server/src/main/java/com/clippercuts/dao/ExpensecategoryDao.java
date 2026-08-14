package com.clippercuts.dao;

import com.clippercuts.entity.Expensecategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensecategoryDao extends JpaRepository<Expensecategory, Integer> { }
