package com.jkk.demo.controller;

import com.jkk.demo.aspect.ValidParam;
import com.jkk.demo.constant.SessionFiled;
import com.jkk.demo.model.Goods;
import com.jkk.demo.model.dal.FiledDictionary;
import com.jkk.demo.model.dal.GoodsInfoDO;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.service.GoodsService;
import com.jkk.demo.util.RestfulRes;
import com.jkk.demo.validator.CanBlankLength;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {
	private final GoodsService goodsService;

	public GoodsController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}

	@GetMapping("self")
	public RestfulRes<List<Goods>> findGoodsSelf(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO){
		return RestfulRes.success(goodsService.findGoodsByUserId(userDO.getId()));
	}

	@ValidParam
	@PostMapping("self")
	public RestfulRes modifyGoods(Integer id,
	                              @RequestParam(required = false) @CanBlankLength(min = 5, max = 80) String goodsName,
	                              @RequestParam(required = false) @Max(999999999) Float goodsActualPrice,
	                              @RequestParam(required = false) @Max(999999999) Integer goodsStock,
	                              @RequestParam(required = false) String goodsDescribe,
	                              @RequestParam(required = false) Boolean goodsStatus,
	                              @SessionAttribute(SessionFiled.USER_DO) UserDO userDO){
		GoodsInfoDO goodsInfoDO = new GoodsInfoDO();
		goodsInfoDO.setId(id);

		if (goodsStatus != null){
			goodsInfoDO.setGoodsStatus(goodsStatus);
		}else {
			if (goodsName != null){
				goodsInfoDO.setGoodsName(goodsName);
			}
			if (goodsActualPrice != null){
				goodsInfoDO.setGoodsActualPrice(goodsActualPrice);
			}
			if (goodsStock != null){
				goodsInfoDO.setGoodsStock(goodsStock);
			}
			if (goodsDescribe != null){
				goodsInfoDO.setGoodsDescribe(goodsDescribe);
			}
		}

		if (goodsService.modifyGoods(goodsInfoDO, userDO.getId())){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("修改失败");
		}
	}

	@PutMapping("self")
	public RestfulRes<Goods> addGoods(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Goods goods, String goodsDescribe){
		Goods goodsOK = goodsService.addGoods(goods, goodsDescribe, userDO.getId());
		if (goodsOK != null){
			return RestfulRes.success(goodsOK);
		}else {
			return RestfulRes.fail("添加商品失败");
		}

	}

	@GetMapping
	public RestfulRes<List<Goods>> getAllGoods(Integer pageNum, Integer page){
		return RestfulRes.success(goodsService.findGoodsByPage(pageNum, page));
	}

	@GetMapping("/{id:[0-9]*}")
	public RestfulRes<Goods> getGoodsById(@PathVariable String id){
		return RestfulRes.success(goodsService.findGoodsById(Integer.parseInt(id)));
	}

	@GetMapping("count")
	public RestfulRes<Long> countAll(){
		return RestfulRes.success(goodsService.countAll());
	}

	@GetMapping("get_describe")
	public RestfulRes<String> getDescribe(Integer id){
		return RestfulRes.success(goodsService.findGoodsDescribeByGoodsId(id));
	}

	@GetMapping("get_type")
	public RestfulRes<String> getType(String dicCode){
		return RestfulRes.success(goodsService.findTypeByDicCode(dicCode));
	}

	@GetMapping("get_next_variety")
	public RestfulRes<List<FiledDictionary>> getNextVariety(String nowCode){
		return RestfulRes.success(goodsService.getNextVariety(nowCode));
	}
}
