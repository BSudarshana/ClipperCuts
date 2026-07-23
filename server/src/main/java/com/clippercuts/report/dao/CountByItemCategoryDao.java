package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByItemCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByItemCategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByItemCategory> countByItemCategory() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByItemCategory(" +
                "c.name, COUNT(i.id)) " +
                "FROM Item i, Subcategory sc, Category c " +
                "WHERE i.subcategory.id = sc.id " +
                "AND sc.category.id = c.id " +
                "GROUP BY c.id",
                CountByItemCategory.class
        ).getResultList();

    }
}