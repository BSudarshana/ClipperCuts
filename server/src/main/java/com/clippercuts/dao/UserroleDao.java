package com.clippercuts.dao;

import com.clippercuts.entity.Userrole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserroleDao extends JpaRepository<Userrole, Integer> {

    Userrole findById(int id);

}
