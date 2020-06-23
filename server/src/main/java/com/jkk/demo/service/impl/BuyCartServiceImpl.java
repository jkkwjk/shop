package com.jkk.demo.service.impl;

import com.jkk.demo.constant.RedisColumn;
import com.jkk.demo.mapper.StoreMapper;
import com.jkk.demo.model.BuyCartItem;
import com.jkk.demo.model.dal.BuyCartGoodsItemDO;
import com.jkk.demo.service.BuyCartService;
import com.jkk.demo.service.GoodsService;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Service
public class BuyCartServiceImpl implements BuyCartService {
	private final GoodsService goodsService;
	private final StoreMapper storeMapper;
	private final ValueOperations valueOperations;

	public BuyCartServiceImpl(GoodsService goodsService, StoreMapper storeMapper, ValueOperations valueOperations) {
		this.goodsService = goodsService;
		this.storeMapper = storeMapper;
		this.valueOperations = valueOperations;
	}


	@Override
	public List<BuyCartItem> getBuyCartByUserId(Integer userId) {
		List<BuyCartGoodsItemDO> buyCartGoodsItemDOList = (List<BuyCartGoodsItemDO>) valueOperations.get(RedisColumn.BUY_CART + userId.toString());

		if (buyCartGoodsItemDOList != null){
			return buyCartGoodsItemDOList.stream().map(this::convert2BuyItem).collect(Collectors.toList());
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<BuyCartItem> deleteBuyCartByGoodsId(Integer userId, Integer goodsId) {
		List<BuyCartGoodsItemDO> buyCartGoodsItemDOList = (List<BuyCartGoodsItemDO>) valueOperations.get(RedisColumn.BUY_CART + userId.toString());
		Assert.notNull(buyCartGoodsItemDOList, "购物车为空不能删除商品 ->" + userId + "->" + goodsId);

		List<BuyCartGoodsItemDO> newBuyCartGoodsItemDOList = buyCartGoodsItemDOList.stream().peek(buyCartGoodsItemDO -> {
			List<BuyCartGoodsItemDO.goodsListItemDO> newGoodsIdList = buyCartGoodsItemDO.getGoodsIdList().stream().filter(t -> !t.getId().equals(goodsId)).collect(Collectors.toList());
			buyCartGoodsItemDO.setGoodsIdList(newGoodsIdList);
		}).filter(t->t.getGoodsIdList().size() != 0).collect(Collectors.toList());

		valueOperations.set(RedisColumn.BUY_CART + userId.toString(), newBuyCartGoodsItemDOList);

		return newBuyCartGoodsItemDOList.stream().map(this::convert2BuyItem).collect(Collectors.toList());
	}

	@Override
	public List<BuyCartItem> addBuyCartByGoodsId(Integer userId, Integer goodsId, Integer num) {
		List<BuyCartGoodsItemDO> buyCartGoodsItemDOList = (List<BuyCartGoodsItemDO>) valueOperations.get(RedisColumn.BUY_CART + userId.toString());

		Integer storeId = goodsService.findGoodsById(goodsId).getStore().getId();

		BuyCartGoodsItemDO.goodsListItemDO goodsListItemDO = new BuyCartGoodsItemDO.goodsListItemDO();
		goodsListItemDO.setId(goodsId);
		goodsListItemDO.setNum(num);

		if (buyCartGoodsItemDOList != null){
			boolean findStore = false;
			for (BuyCartGoodsItemDO cartGoodsItemDO : buyCartGoodsItemDOList) {
				if (cartGoodsItemDO.getStoreId().equals(storeId)) {
					// 存在店铺
					findStore = true;
					List<BuyCartGoodsItemDO.goodsListItemDO> foundGoodsList = cartGoodsItemDO.getGoodsIdList();

					boolean findGoods = false;
					for (BuyCartGoodsItemDO.goodsListItemDO goodsListItem : foundGoodsList) {
						if (goodsListItem.getId().equals(goodsId)) {
							findGoods = true;
							goodsListItem.setNum(goodsListItem.getNum() + num);
							break;
						}
					}

					if (!findGoods){
						// 不存在该商品
						foundGoodsList.add(goodsListItemDO);
					}
					break;
				}
			}

			if (!findStore){
				// 不存在店铺
				BuyCartGoodsItemDO buyCartGoodsItemDO = new BuyCartGoodsItemDO();
				buyCartGoodsItemDO.setStoreId(storeId);
				buyCartGoodsItemDO.setGoodsIdList(Arrays.asList(goodsListItemDO)); // 不要信IDEA的
				buyCartGoodsItemDOList.add(buyCartGoodsItemDO);
			}


		}else {
			buyCartGoodsItemDOList = new ArrayList<>();

			BuyCartGoodsItemDO buyCartGoodsItemDO = new BuyCartGoodsItemDO();
			buyCartGoodsItemDO.setStoreId(storeId);
			buyCartGoodsItemDO.setGoodsIdList(Arrays.asList(goodsListItemDO)); // 不要信IDEA的
			buyCartGoodsItemDOList.add(buyCartGoodsItemDO);
		}


		valueOperations.set(RedisColumn.BUY_CART + userId.toString(), buyCartGoodsItemDOList);
		return buyCartGoodsItemDOList.stream().map(this::convert2BuyItem).collect(Collectors.toList());
	}

	private BuyCartItem convert2BuyItem(BuyCartGoodsItemDO buyCartGoodsItemDO){
		BuyCartItem buyCartItem = new BuyCartItem();
		buyCartItem.setStore(storeMapper.findById(buyCartGoodsItemDO.getStoreId()).orElseThrow(
				()->new IllegalArgumentException("找不到店铺 ->"+buyCartGoodsItemDO.getStoreId())
		));

		List<BuyCartItem.GoodsInBuyCart> goodsInfoList =
				buyCartGoodsItemDO.getGoodsIdList().stream().map(goodsListItemDO -> {
					BuyCartItem.GoodsInBuyCart goodsInBuyCart = new BuyCartItem.GoodsInBuyCart();

					goodsInBuyCart.setNum(goodsListItemDO.getNum());
					goodsInBuyCart.setGoodsInfo(goodsService.findGoodsById(goodsListItemDO.getId()));

					return goodsInBuyCart;
				}).collect(Collectors.toList());


		buyCartItem.setGoodsList(goodsInfoList);

		return buyCartItem;
	}

}
