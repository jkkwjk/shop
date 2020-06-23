package com.jkk.demo.controller;

import com.jkk.demo.constant.SessionFiled;
import com.jkk.demo.model.BuyCartItem;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.service.BuyCartService;
import com.jkk.demo.util.RestfulRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("buy_cart")
public class BuyCartController {
	private final BuyCartService buyCartService;

	public BuyCartController(BuyCartService buyCartService) {
		this.buyCartService = buyCartService;
	}

	@GetMapping
	public RestfulRes<List<BuyCartItem>> getBuyCartSelf(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO){
		return RestfulRes.success(buyCartService.getBuyCartByUserId(userDO.getId()));
	}

	@DeleteMapping
	public RestfulRes<List<BuyCartItem>> deleteBuyCartSelf(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Integer goodsId){
		return RestfulRes.success(buyCartService.deleteBuyCartByGoodsId(userDO.getId(), goodsId));
	}

	@PutMapping
	public RestfulRes<List<BuyCartItem>> addBuyCartSelf(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Integer goodsId, Integer num){
		return RestfulRes.success(buyCartService.addBuyCartByGoodsId(userDO.getId(), goodsId, num));
	}
}
