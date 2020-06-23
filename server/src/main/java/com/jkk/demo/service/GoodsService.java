package com.jkk.demo.service;

import com.jkk.demo.model.Goods;
import com.jkk.demo.model.dal.FiledDictionary;
import com.jkk.demo.model.dal.GoodsInfoDO;

import java.util.List;

public interface GoodsService {
	Goods addGoods(Goods goods, String goodsDescribe, Integer userId);

	Boolean modifyGoods(GoodsInfoDO goodsInfoDO, Integer userId);

	List<Goods> findGoodsByUserId(Integer userId);

	String findGoodsDescribeByGoodsId(Integer id);
	/**
	 * 通过字典序号获得商品的类型
	 * @param dicCode
	 * @return
	 */
	String findTypeByDicCode(String dicCode);

	List<FiledDictionary> getNextVariety(String nowCode);



	List<Goods> findGoodsByPage(Integer pageSize, Integer page);

	Long countAll();

	Goods findGoodsById(Integer id);
}
