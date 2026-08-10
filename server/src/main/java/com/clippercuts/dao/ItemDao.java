package com.clippercuts.dao;

import com.clippercuts.entity.Item;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ItemDao extends JpaRepository<Item,Integer> {

//    @Query("select i from Item i where i.id = :id")
//    Item findItemById(@Param("id") Integer id);
//
//    @Query("select i from Item i where i.name = :name")
//    Item findItemByName(String name);
//
//    @Query("select i from Item i where i.itemnumber = :itemnumber")
//    Item findByItemNumber(String itemnumber);

    @Override
    @EntityGraph(attributePaths = {"itemstatus", "unittype", "itembrand", "subcategory", "subcategory.category"})
    List<Item> findAll();

    @Override
    @EntityGraph(attributePaths = {"itemstatus", "unittype", "itembrand", "subcategory", "subcategory.category"})
    Optional<Item> findById(Integer id);

    Optional<Item> findByItemnumberIgnoreCase(String itemnumber);
    Optional<Item> findByNameIgnoreCase(String name);
    boolean existsByItemnumberIgnoreCaseAndIdNot(String itemnumber, Integer id);
    boolean existsByNameIgnoreCaseAndIdNot(String name, Integer id);

}

