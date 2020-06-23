package com.jkk.service.address.controller;

import com.jkk.service.address.dao.CityDao;
import com.jkk.service.address.entity.City;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("city")
public class CityController {
	private final CityDao cityDao;

	public CityController(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@RequestMapping("")
	public List<City> getCity(@RequestParam(required = false) String province) {
		if (province == null) {
			return cityDao.findAll();
		}else {
			return cityDao.findByProvinceCode(province);
		}
	}

	@RequestMapping("{code}")
	public Optional<City> getCityByCode(@PathVariable String code) {
		return cityDao.findById(code);
	}


}
