package com.jkk.demo.controller;

import com.jkk.demo.constant.SessionFiled;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.model.request.OrderRequest;
import com.jkk.demo.service.OrderService;
import com.jkk.demo.util.RestfulRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public RestfulRes getOrderSelf(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Integer status){
		return RestfulRes.success(orderService.getOrderSelfByStatus(status, userDO.getId()));
	}

	@PostMapping
	public RestfulRes addOrderSelf(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO,@RequestBody OrderRequest orderRequest){
		orderService.addOrder(orderRequest, userDO.getId());
		return RestfulRes.success();
	}

	@GetMapping("get_describe_in_snapshot")
	public RestfulRes getDescribeInSnapshot(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Integer orderId, Integer goodsId){
		return RestfulRes.success(orderService.getDescribeInSnapshot(orderId, userDO.getId(), goodsId));
	}
}
