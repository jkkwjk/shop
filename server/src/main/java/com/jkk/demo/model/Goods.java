package com.jkk.demo.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.jkk.demo.service.StoreService;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class Goods {

	private Integer id;

	@NotBlank
	private String goodsName;

	@NotNull
	private Float goodsPrice;

	@NotNull
	private Float goodsActualPrice;

	@Min(1)
	@Max(999999999)
	private Integer goodsStock;

	@NotBlank
	private String goodsPic;

	private Store store;

	@NotBlank
	private String dicCode;

	@NotNull
	private Map<String, String> prop;

}
