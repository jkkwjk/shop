package com.jkk.demo.model.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
	private String address;

	private String name;

	private String telephone;

	private List<GoodsOrderRequest> goodsInOrder; //id num totalMoney

	private Boolean fromBuyCart;
}
