package com.clippercuts.dao;

import com.clippercuts.entity.Appointment;
import com.clippercuts.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingDao extends JpaRepository<Rating,Integer> {

}
