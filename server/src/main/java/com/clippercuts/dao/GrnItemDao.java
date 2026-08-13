package com.clippercuts.dao;

import com.clippercuts.entity.GrnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;

public interface GrnItemDao extends JpaRepository<GrnItem,Integer> {

    @Query("select coalesce(sum(gi.quantity), 0) from GrnItem gi " +
            "where gi.goodReceiveNote.purchaseorder.id = :poId and " +
            "gi.item.id = :itemId")
    BigDecimal totalReceived(@Param("poId") Integer poId, @Param("itemId") Integer itemId);

}

