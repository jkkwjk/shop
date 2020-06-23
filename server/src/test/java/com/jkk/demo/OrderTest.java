package com.jkk.demo;

import com.jkk.demo.model.request.GoodsOrderRequest;
import com.jkk.demo.model.request.OrderRequest;
import com.jkk.demo.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderTest {
	@Autowired
	private OrderService orderService;
	@Test
	void test1(){
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setAddress("地址");
		orderRequest.setName("annotation");
		orderRequest.setTelephone("13456466334");

		List<GoodsOrderRequest> list = new ArrayList<>();
		GoodsOrderRequest goodsOrderRequest = new GoodsOrderRequest();
		goodsOrderRequest.setId(2);
		goodsOrderRequest.setNum(2);
		goodsOrderRequest.setTotalMoney(2.46F);

		GoodsOrderRequest goodsOrderRequest1 = new GoodsOrderRequest();
		goodsOrderRequest1.setId(4);
		goodsOrderRequest1.setNum(1);
		goodsOrderRequest1.setTotalMoney(59F);


		list.add(goodsOrderRequest);
		list.add(goodsOrderRequest1);
		orderRequest.setGoodsInOrder(list);
		orderService.addOrder(orderRequest, 1);
	}
}
