package com.clippercuts.dao;

import com.clippercuts.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemDao extends JpaRepository<Item,Integer> {

    @Query("select i from Item i where i.id = :id")
    Item findItemById(@Param("id") Integer id);

    @Query("select i from Item i where i.name = :name")
    Item findItemByName(String name);

    @Query("select i from Item i where i.itemnumber = :itemnumber")
    Item findByItemNumber(String itemnumber);

}

