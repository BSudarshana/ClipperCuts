package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Customerstatus;
import lk.earth.earthuniversity.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerstatusDao extends JpaRepository<Customerstatus,Integer> {

}

