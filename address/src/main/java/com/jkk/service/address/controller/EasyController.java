package com.jkk.service.address.controller;

import com.jkk.service.address.entity.Area;
import com.jkk.service.address.entity.City;
import com.jkk.service.address.entity.Province;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EasyController {
	private final ProvinceController provinceController;
	private final CityController cityController;
	private final AreaController areaController;

	public EasyController(ProvinceController provinceController, CityController cityController, AreaController areaController) {
		this.provinceController = provinceController;
		this.cityController = cityController;
		this.areaController = areaController;
	}

	/**
	 * 简易使用
	 * @param a
	 * "" 将获得 所有省份
	 * "11" 将获得 省下面所有的市
	 * "11-1231" 获得1231下的所有的区
	 * @return
	 */
	@RequestMapping(value = "")
	public Object getInfoFromPayLoad(String a) {
		if (a == null) return this.provinceController.getAllProvince();

		if (a.equals("")) {
			return this.provinceController.getAllProvince();
		}else if (a.length() == 2) {
			return this.cityController.getCity(a);
		}else if (a.length() == 7){
			return this.areaController.getArea(a.substring(3)); //注意: 再次增加下一级需要修改增加 end位置
		}else {
			return null;
		}
	}

	/**
	 *
	 * @param a 格式必须为 15-1501-150102
	 * @return 对应的地址
	 */
	@RequestMapping(value = "get_info")
	public String getAddressInfoByCodeArray(String a) {
		Assert.isTrue(StringUtils.length(a)>7 && a.charAt(2)=='-' && a.charAt(7)=='-',"输入的格式必须为 xx-xxxx-xx");
		Optional<Province> province = provinceController.getProvinceByCode(a.substring(0,2));
		Optional<City> city = cityController.getCityByCode(a.substring(3,7));
		Optional<Area> areaName = areaController.getAreaByCode(a.substring(8));

		if (province.isPresent() && city.isPresent() &&areaName.isPresent()) {
			return province.get().getName() + city.get().getName() + areaName.get().getName();
		}else {
			return "";
		}

	}

	/**
	 * 批量查询对应的地址
	 * @param a 格式必须为 [15-1501-150102,15-1501-150102]
	 * @return 对应的地址
	 */
	@RequestMapping(value = "mget_info")
	public List<String> getAddressInfoByCodeArray(@RequestBody Map<String,String[]> a) {
		return Stream.of(a.get("a")).map(this::getAddressInfoByCodeArray).collect(Collectors.toList());
	}
}
