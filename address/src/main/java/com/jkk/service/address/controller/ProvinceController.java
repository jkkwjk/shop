package com.jkk.service.address.controller;

import com.jkk.service.address.dao.ProvinceDao;
import com.jkk.service.address.entity.Province;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("province")
public class ProvinceController {
	private final ProvinceDao provinceDao;

	public ProvinceController(ProvinceDao provinceDao) {
		this.provinceDao = provinceDao;
	}

	@RequestMapping("")
	public List<Province> getAllProvince() {
		return provinceDao.findAll();
	}

	@RequestMapping("{code}")
	public Optional<Province> getProvinceByCode(@PathVariable String code) {
		return provinceDao.findById(code);
	}
}
