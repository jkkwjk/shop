package com.jkk.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.jkk.demo.constant.OrderStatus;
import com.jkk.demo.mapper.GoodsInfoMapper;
import com.jkk.demo.mapper.OrderGoodsMapper;
import com.jkk.demo.mapper.OrderMapper;
import com.jkk.demo.mapper.StoreMapper;
import com.jkk.demo.model.BuyCartItem;
import com.jkk.demo.model.Goods;
import com.jkk.demo.model.Order;
import com.jkk.demo.model.Store;
import com.jkk.demo.model.dal.GoodsInfoDO;
import com.jkk.demo.model.dal.OrderDO;
import com.jkk.demo.model.dal.OrderGoodsDO;
import com.jkk.demo.model.request.GoodsOrderRequest;
import com.jkk.demo.model.request.OrderRequest;
import com.jkk.demo.service.BuyCartService;
import com.jkk.demo.service.GoodsService;
import com.jkk.demo.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	private GoodsService goodsService;
	private GoodsInfoMapper goodsInfoMapper;
	private OrderMapper orderMapper;
	private OrderGoodsMapper orderGoodsMapper;
	private BuyCartService buyCartService;
	private StoreMapper storeMapper;

	public OrderServiceImpl(GoodsService goodsService, OrderMapper orderMapper, OrderGoodsMapper orderGoodsMapper, GoodsInfoMapper goodsInfoMapper, BuyCartService buyCartService, StoreMapper storeMapper) {
		this.goodsService = goodsService;
		this.orderMapper = orderMapper;
		this.orderGoodsMapper = orderGoodsMapper;
		this.goodsInfoMapper = goodsInfoMapper;
		this.buyCartService = buyCartService;
		this.storeMapper = storeMapper;
	}

	@Transactional
	@Override
	public void addOrder(OrderRequest orderRequest, Integer userId) {
		OrderDO orderDO = new OrderDO();
		orderDO.setStatus(3); //直接设置为已完成
		orderDO.setUserId(userId);
		orderDO.setAddress(orderRequest.getAddress());
		orderDO.setName(orderRequest.getName());
		orderDO.setTelephone(orderRequest.getTelephone());

		OrderDO orderDOSaved = orderMapper.save(orderDO);
		Assert.notNull(orderDOSaved, "orderDO保存失败 ->" + orderDO);

		Integer orderId = orderDOSaved.getId();
		List<OrderGoodsDO> orderGoodsDOList = orderRequest.getGoodsInOrder().stream().map(this::convert2OrderGoodsDO).peek(t->t.setOrderId(orderId)).collect(Collectors.toList());
		Assert.isTrue(orderGoodsMapper.saveAll(orderGoodsDOList).size() != 0, "orderGoodsDOList保存失败 ->" + orderGoodsDOList);

		// 减少商品库存
		orderGoodsDOList.forEach(orderGoodsDO -> {
			GoodsInfoDO goodsInfoDO = goodsInfoMapper.findById(orderGoodsDO.getGoodsId()).orElseThrow(()-> new IllegalArgumentException("找不到该商品 ->" + orderGoodsDO.getGoodsId()));
			int nowStock = goodsInfoDO.getGoodsStock() - orderGoodsDO.getGoodsNum();
			Assert.isTrue(nowStock >= 0, "商品被抢光了哦");
			goodsInfoDO.setGoodsStock(nowStock);
			goodsService.modifyGoods(goodsInfoDO, userId);
		});

		//如果从购物车过来的则删除购物车中的商品
		if (orderRequest.getFromBuyCart()){
			orderGoodsDOList.forEach(orderGoodsDO -> buyCartService.deleteBuyCartByGoodsId(userId, orderGoodsDO.getGoodsId()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrderSelfByStatus(Integer status, Integer userId) {
		List<OrderDO> orderDOList = orderMapper.findAllByStatusAndUserIdOrderByCreateTimeDesc(status, userId);

		return orderDOList.stream().map(orderDO -> {
			// 获得订单的基本信息
			Order order = new Order();
			BeanUtils.copyProperties(orderDO, order);
			order.setStatus(OrderStatus.getStatusString(orderDO.getStatus()));
			return order;
		}).peek(order -> {
			List<OrderGoodsDO> orderGoodsDOList = orderGoodsMapper.findAllByOrderIdOrderByStoreId(order.getId());

			List<BuyCartItem> orderItemList = new ArrayList<>();

			for (int i=0; i<orderGoodsDOList.size();){
				BuyCartItem orderItem = new BuyCartItem();
				int storeId = orderGoodsDOList.get(i).getStoreId();
				Store store = storeMapper.findById(storeId).orElseThrow(()->new IllegalArgumentException("店铺找不到店铺 ->"+storeId));
				orderItem.setStore(store);

				List<BuyCartItem.GoodsInBuyCart> goodsInBuyCartList = new ArrayList<>();
				while (i < orderGoodsDOList.size() && orderGoodsDOList.get(i).getStoreId() == storeId){
					// 当前店铺的下一个商品
					BuyCartItem.GoodsInBuyCart goodsInBuyCart = new BuyCartItem.GoodsInBuyCart();
					goodsInBuyCart.setNum(orderGoodsDOList.get(i).getGoodsNum());

					Goods goods = new Goods();
					BeanUtils.copyProperties(orderGoodsDOList.get(i), goods);
					goods.setId(orderGoodsDOList.get(i).getGoodsId());
					goods.setStore(store);
					goods.setProp(JSON.parseObject(orderGoodsDOList.get(i).getProp(), Map.class));
					goodsInBuyCart.setGoodsInfo(goods);

					goodsInBuyCartList.add(goodsInBuyCart);
					++i;
				}

				orderItem.setGoodsList(goodsInBuyCartList);

				orderItemList.add(orderItem);
			}

			order.setBuyCartItemList(orderItemList);
		}).collect(Collectors.toList());

	}

	@Override
	public String getDescribeInSnapshot(Integer orderId, Integer userId, Integer goodsId) {
		Assert.notNull(orderMapper.findByIdAndUserId(orderId, userId), "找不到该商品ID ->"+orderId+"->"+userId);

		return orderGoodsMapper.findByOrderIdAndGoodsId(orderId, goodsId).getGoodsDescribe();
	}

	private OrderGoodsDO convert2OrderGoodsDO(GoodsOrderRequest goodsOrderRequest){
		OrderGoodsDO orderGoodsDO = new OrderGoodsDO();
		orderGoodsDO.setGoodsId(goodsOrderRequest.getId());
		orderGoodsDO.setGoodsNum(goodsOrderRequest.getNum());

		Goods goods = goodsService.findGoodsById(goodsOrderRequest.getId());
		BeanUtils.copyProperties(goods, orderGoodsDO);
		orderGoodsDO.setId(null); // copyProperties会将goods中的id 拷贝到 orderGoodsDO 中
		orderGoodsDO.setGoodsDescribe(goodsService.findGoodsDescribeByGoodsId(goodsOrderRequest.getId()));
		orderGoodsDO.setStoreId(goods.getStore().getId());
		orderGoodsDO.setProp(JSON.toJSONString(goods.getProp()));

		// Assert.isTrue(
		// 		Float.compare(goodsOrderRequest.getTotalMoney(),orderGoodsDO.getGoodsNum()*orderGoodsDO.getGoodsActualPrice())==0,
		// 		"订单数据更新, 请重试"); //fixme 不能比较3.99与4.00的大小 可用相减绝对值<eps

		return orderGoodsDO;
	}
}
