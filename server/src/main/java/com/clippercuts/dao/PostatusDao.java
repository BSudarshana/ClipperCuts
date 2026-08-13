package com.clippercuts.dao;

import com.clippercuts.entity.Postatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostatusDao extends JpaRepository<Postatus,Integer>{
    Optional<Postatus> findByNameIgnoreCase(String name);
}

