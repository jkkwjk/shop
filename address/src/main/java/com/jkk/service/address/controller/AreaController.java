package com.jkk.service.address.controller;

import com.jkk.service.address.dao.AreaDao;
import com.jkk.service.address.entity.Area;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("area")
public class AreaController {
	private final AreaDao areaDao;

	public AreaController(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

	@RequestMapping("")
	public List<Area> getArea(@RequestParam(required = false) String city) {
		if (city == null) {
			return areaDao.findAll();
		}else {
			return areaDao.findByCityCode(city);
		}
	}

	@RequestMapping("{code}")
	public Optional<Area> getAreaByCode(@PathVariable String code) {
		return areaDao.findById(code);
	}

}
