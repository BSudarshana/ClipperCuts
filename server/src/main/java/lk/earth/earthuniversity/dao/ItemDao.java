package lk.earth.earthuniversity.dao;

import lk.earth.earthuniversity.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ItemDao extends JpaRepository<Item,Integer> {

}

