package com.jkk.service.address.dao;

import com.jkk.service.address.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CityDao extends JpaRepository<City, String> {
	List<City> findByProvinceCode(String provinceCode);
}
