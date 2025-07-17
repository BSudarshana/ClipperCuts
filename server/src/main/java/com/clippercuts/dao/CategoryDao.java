package com.clippercuts.dao;

import com.clippercuts.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category,Integer> {

}

