package com.clippercuts.dao;

import com.clippercuts.entity.Stockwriteoff;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockwriteoffDao extends JpaRepository<Stockwriteoff, Integer> {

    @EntityGraph(attributePaths = {"location", "createdByUser", "items", "items.item", "items.item.unittype"})
    @Query("select distinct w from Stockwriteoff w order by w.id desc")
    List<Stockwriteoff> findAllDetailed();

    @EntityGraph(attributePaths = {"location", "createdByUser", "items", "items.item", "items.item.unittype"})
    @Query("select distinct w from Stockwriteoff w where w.id = :id")
    Optional<Stockwriteoff> findDetailedById(@Param("id") Integer id);

    @Query(
            value = "SELECT writeoffnumber FROM stockwriteoff " +
                    "WHERE writeoffnumber LIKE CONCAT('SWO-', :year, '-%') " +
                    "ORDER BY id DESC LIMIT 1",
            nativeQuery = true
    )
    String getLastWriteOffNumberByYear(@Param("year") int year);
}
