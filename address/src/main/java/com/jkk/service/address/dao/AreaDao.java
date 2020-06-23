package com.jkk.service.address.dao;

import com.jkk.service.address.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaDao extends JpaRepository<Area, String> {
	List<Area> findByCityCode(String cityCode);
}
