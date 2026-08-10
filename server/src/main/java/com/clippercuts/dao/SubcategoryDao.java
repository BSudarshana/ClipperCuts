package com.clippercuts.dao;

import com.clippercuts.entity.Subcategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubcategoryDao extends JpaRepository<Subcategory, Integer> {
    @EntityGraph(attributePaths = "category")
    List<Subcategory> findByCategory_IdOrderByName(Integer categoryId);

    @Override
    @EntityGraph(attributePaths = "category")
    List<Subcategory> findAll();
}
