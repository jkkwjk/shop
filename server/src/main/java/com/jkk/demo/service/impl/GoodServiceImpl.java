package com.jkk.demo.service.impl;

import com.jkk.demo.mapper.FiledDictionaryMapper;
import com.jkk.demo.mapper.GoodsInfoMapper;
import com.jkk.demo.mapper.GoodsPropertyMapper;
import com.jkk.demo.mapper.StoreMapper;
import com.jkk.demo.model.Goods;
import com.jkk.demo.model.Store;
import com.jkk.demo.model.dal.FiledDictionary;
import com.jkk.demo.model.dal.GoodsInfoDO;
import com.jkk.demo.model.dal.GoodsPropertyDO;
import com.jkk.demo.service.GoodsService;
import com.jkk.demo.util.BeanUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GoodServiceImpl implements GoodsService {
	private final FiledDictionaryMapper filedDictionaryMapper;
	private final GoodsInfoMapper goodsInfoMapper;
	private final GoodsPropertyMapper goodsPropertyMapper;
	private final StoreMapper storeMapper;

	public GoodServiceImpl(FiledDictionaryMapper filedDictionaryMapper, GoodsInfoMapper goodsInfoMapper, GoodsPropertyMapper goodsPropertyMapper, StoreMapper storeMapper) {
		this.filedDictionaryMapper = filedDictionaryMapper;
		this.goodsInfoMapper = goodsInfoMapper;
		this.goodsPropertyMapper = goodsPropertyMapper;
		this.storeMapper = storeMapper;
	}


	@Transactional
	@Override
	public Goods addGoods(Goods goods, String goodsDescribe, Integer userId) {
		GoodsInfoDO goodsInfoDO = convert2GoodsInfoDO(goods, userId);
		goodsInfoDO.setGoodsStatus(true);
		goodsInfoDO.setGoodsDescribe(goodsDescribe);

		List<GoodsPropertyDO> goodsPropertyDOList = convert2GoodsPropertyDOS(goods);
		GoodsInfoDO insertedGoodsInfoDO = goodsInfoMapper.save(goodsInfoDO);
		if (goodsPropertyDOList.stream().peek(goodsPropertyDO -> goodsPropertyDO.setGoodsId(insertedGoodsInfoDO.getId())).map(goodsPropertyMapper::save).noneMatch(Objects::isNull)){
			return convert2Goods(insertedGoodsInfoDO);
		}else {
			return null;
		}
	}

	/**
	 * 只能修改商品最基本的信息(不能修改商品类型)
	 */
	@Override
	public Boolean modifyGoods(GoodsInfoDO goodsInfoDO, Integer userId) {
		if (goodsInfoMapper.countByIdAndUserIdAndGoodsStatus(goodsInfoDO.getId(), userId, true) == 1){
			GoodsInfoDO goodsInfoDODB = goodsInfoMapper.findById(goodsInfoDO.getId()).orElse(new GoodsInfoDO());
			BeanUtil.copyNullProperties(goodsInfoDODB, goodsInfoDO);
			return goodsInfoMapper.save(goodsInfoDO) != null;
		}else {
			return false;
		}
	}

	@Override
	public List<Goods> findGoodsByUserId(Integer userId) {
		List<GoodsInfoDO> goodsInfoDOList = goodsInfoMapper.findAllByStoreIdAndGoodsStatus(storeMapper.findByUserId(userId).getId(), true);
		return goodsInfoDOList.stream().map(this::convert2Goods).collect(Collectors.toList());
	}

	@Override
	public String findGoodsDescribeByGoodsId(Integer id) {
		GoodsInfoDO goodsInfoDO = goodsInfoMapper.findByIdAndGoodsStatus(id, true);
		Assert.notNull(goodsInfoDO, id + "-> 未找到该商品");
		return goodsInfoDO.getGoodsDescribe();
	}

	@Override
	public String findTypeByDicCode(String dicCode) {
		FiledDictionary filedDictionary = filedDictionaryMapper.findById(dicCode)
				.orElseThrow(() -> new IllegalArgumentException("找不到对应的dicCode -> " + dicCode));
		return filedDictionary.getFieldName();
	}

	@Override
	public List<FiledDictionary> getNextVariety(String nowCode) {
		String code = nowCode + "__";
		return filedDictionaryMapper.findByCodeIsLike(code);
	}

	@Override
	public List<Goods> findGoodsByPage(Integer pageSize, Integer page) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return goodsInfoMapper.findAllByGoodsStatus(true, pageable).stream().map(this::convert2Goods).collect(Collectors.toList());
	}

	@Override
	public Long countAll() {
		return goodsInfoMapper.countByGoodsStatus(true);
	}

	@Override
	public Goods findGoodsById(Integer id) {
		return convert2Goods(goodsInfoMapper.findByIdAndGoodsStatus(id, true));
	}


	private Goods convert2Goods(GoodsInfoDO goodsInfoDO) {
		Goods goods = new Goods();
		BeanUtils.copyProperties(goodsInfoDO, goods);

		// 通过id找到商品的属性
		List<GoodsPropertyDO> goodsPropertyDOList = goodsPropertyMapper.findAllByGoodsId(goodsInfoDO.getId());
		Map<String, String> prop = goodsPropertyDOList.stream().collect(Collectors.toMap(GoodsPropertyDO::getPropertyName, GoodsPropertyDO::getPropertyValue));
		goods.setProp(prop);

		// 通过storeId找到店铺名称和描述
		Store store = storeMapper.findById(goodsInfoDO.getStoreId())
				.orElseThrow(() -> new IllegalArgumentException("找不到对应的店铺 -> " + goodsInfoDO));
//		goods.setStoreName(store.getStoreName());
//		goods.setStoreDescribe(store.getStoreDescribe());
//		goods.setStoreCredit(store.getCredit());
//		goods.setStoreId(store.getId());
		goods.setStore(store);
		return goods;
	}

	private GoodsInfoDO convert2GoodsInfoDO(Goods goods, Integer userId) {
		GoodsInfoDO goodsInfoDO = new GoodsInfoDO();
		BeanUtils.copyProperties(goods, goodsInfoDO);

		Assert.isTrue(filedDictionaryMapper.findById(goods.getDicCode()).isPresent(),"找不到对应的dicCode -> " + goods);

		if (goods.getId() != null){
			Assert.isTrue(goodsInfoMapper.countByIdAndUserIdAndGoodsStatus(goods.getId(), userId, true) == 1,
					"用户没有对该商品的操做权限 -> goodsId" + goods.getId() + " -> userId" + userId);
		}else {
			// 对新增商品设置店铺id
			goodsInfoDO.setStoreId(storeMapper.findByUserId(userId).getId());
		}

		return goodsInfoDO;
	}

	private List<GoodsPropertyDO> convert2GoodsPropertyDOS(Goods goods) {
		FiledDictionary filedDictionary = filedDictionaryMapper.findById(goods.getDicCode())
				.orElseThrow(() -> new IllegalArgumentException("找不到对应的dicCode -> " + goods));
		Set<String> filedValue = Stream.of(StringUtils.split(filedDictionary.getFieldValue(),"|")).collect(Collectors.toSet());
		Assert.isTrue(goods.getProp().keySet().equals(filedValue), "商品属性对应不全 -> " + goods);

		return goods.getProp().entrySet().stream().map(t -> {
			GoodsPropertyDO goodsPropertyDO = new GoodsPropertyDO();
			goodsPropertyDO.setPropertyName(t.getKey());
			goodsPropertyDO.setPropertyValue(t.getValue());
			return goodsPropertyDO;
		}).collect(Collectors.toList());
	}
}
