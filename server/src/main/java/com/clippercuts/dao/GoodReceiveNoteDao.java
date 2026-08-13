package com.clippercuts.dao;

import com.clippercuts.entity.GoodReceiveNote;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface GoodReceiveNoteDao extends JpaRepository<GoodReceiveNote,Integer> {
    @Override @EntityGraph(attributePaths={
            "grnStatus",
            "employee",
            "purchaseorder",
            "purchaseorder.supplier",
            "location",
            "receivedByUser",
            "grnItems",
            "grnItems.item"
    })
    List<GoodReceiveNote> findAll();

    @EntityGraph(attributePaths={
            "grnStatus",
            "employee",
            "purchaseorder",
            "purchaseorder.supplier",
            "location",
            "receivedByUser",
            "grnItems",
            "grnItems.item"
    })

    @Query("select g from GoodReceiveNote g where g.id=:id") Optional<GoodReceiveNote>
    findDetailedById(@Param("id") Integer id);

    @Query(value="SELECT grn_number FROM good_receive_note " +
            "WHERE grn_number LIKE CONCAT('GRN-',:year,'-%') " +
            "ORDER BY CAST(SUBSTRING_INDEX(grn_number,'-',-1) AS UNSIGNED) " +
            "DESC LIMIT 1",nativeQuery=true)
    String getLastGrnByYear(@Param("year") Integer year);
}
