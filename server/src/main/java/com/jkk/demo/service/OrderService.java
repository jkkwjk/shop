package com.jkk.demo.service;

import com.jkk.demo.model.Order;
import com.jkk.demo.model.request.OrderRequest;

import java.util.List;

public interface OrderService {
	void addOrder(OrderRequest orderRequest, Integer userId);

	List<Order> getOrderSelfByStatus(Integer status, Integer userId);

	String getDescribeInSnapshot(Integer orderId, Integer userId, Integer goodsId);
}
