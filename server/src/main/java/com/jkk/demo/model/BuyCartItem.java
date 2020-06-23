package com.jkk.demo.model;

import lombok.Data;

import java.util.List;

@Data
public class BuyCartItem {
	@Data
	public static class GoodsInBuyCart{
		private Goods goodsInfo;
		private Integer num;
	}

	private Store store;

	private List<GoodsInBuyCart> goodsList;
}
