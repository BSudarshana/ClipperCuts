package com.clippercuts.dao;

import com.clippercuts.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDao extends JpaRepository<Item,Integer> {

}

