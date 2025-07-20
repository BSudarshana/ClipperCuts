package com.clippercuts.dao;

import com.clippercuts.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PackageDao extends JpaRepository<Package,Integer> {


    @Query("select p from Package p where p.id = :id")
    Package findByPackageId(@Param("id") Integer id);

    @Query("select p from Package p where p.name = :name")
    Package findByPackageName(String name);

    @Query("select p from Package p where p.packagnumber = :packagnumber")
    Package findByPackageNumber(String packagnumber);

}

