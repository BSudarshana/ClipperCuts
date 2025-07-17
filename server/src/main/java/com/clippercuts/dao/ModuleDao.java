package com.clippercuts.dao;

import com.clippercuts.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ModuleDao extends JpaRepository<Module,Integer> {
}
