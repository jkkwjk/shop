package com.jkk.demo.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Order {
	private int id;

	private String address;

	private String name;

	private String telephone;

	private Date createTime;

	private String status;

	private List<BuyCartItem> buyCartItemList; // 暂时使用购物车的类 建议重构
}
