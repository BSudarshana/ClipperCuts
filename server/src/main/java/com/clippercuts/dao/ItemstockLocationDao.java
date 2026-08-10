package com.clippercuts.dao;

import com.clippercuts.entity.ItemstockLocation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemstockLocationDao extends JpaRepository<ItemstockLocation, Integer> {

    @Query("select coalesce(sum(s.quantity), 0) " +
            "from ItemstockLocation s where s.item.id = :itemId")
    BigDecimal totalStock(@Param("itemId") Integer itemId);

    boolean existsByItem_Id(Integer itemId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from ItemstockLocation s " +
            "where s.item.id = :itemId and s.location.id = :locationId")
    Optional<ItemstockLocation> findForUpdate(
            @Param("itemId") Integer itemId,
            @Param("locationId") Integer locationId
    );

    @EntityGraph(attributePaths = {
            "item",
            "item.unittype"
    })
    @Query("select s from ItemstockLocation s " +
            "where s.location.id = :locationId " +
            "and s.quantity > 0 " +
            "order by s.item.name")
    List<ItemstockLocation> findAvailableByLocation(
            @Param("locationId") Integer locationId
    );
}