package com.jkk.demo.service;

import com.jkk.demo.model.BuyCartItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BuyCartService {
	List<BuyCartItem> getBuyCartByUserId(Integer userId);

	List<BuyCartItem> deleteBuyCartByGoodsId(Integer userId, Integer goodsId);

	List<BuyCartItem> addBuyCartByGoodsId(Integer userId, Integer goodsId, Integer num);
}
