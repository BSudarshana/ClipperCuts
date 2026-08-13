package com.clippercuts.dao;

import com.clippercuts.entity.GrnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GrnStatusDao extends JpaRepository<GrnStatus,Integer>{
    Optional<GrnStatus> findByNameIgnoreCase(String name);
}


