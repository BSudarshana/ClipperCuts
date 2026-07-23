package com.clippercuts.report.dao;

import com.clippercuts.report.entity.CountByServiceCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CountByServiceCategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CountByServiceCategory> countByServiceCategory() {

        return entityManager.createQuery(
                "SELECT NEW com.clippercuts.report.entity.CountByServiceCategory(" +
                "sc.name, COUNT(s.id)) " +
                "FROM Service s, Servicecategory sc " +
                "WHERE s.servicecategory.id = sc.id " +
                "GROUP BY sc.id",
                CountByServiceCategory.class
        ).getResultList();

    }
}