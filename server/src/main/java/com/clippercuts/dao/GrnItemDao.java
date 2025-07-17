package com.clippercuts.dao;

import com.clippercuts.entity.GrnItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrnItemDao extends JpaRepository<GrnItem,Integer> {

}

