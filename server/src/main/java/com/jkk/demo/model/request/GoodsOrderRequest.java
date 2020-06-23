package com.jkk.demo.model.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsOrderRequest extends GoodsNumRequest {
	private Float totalMoney;
}
