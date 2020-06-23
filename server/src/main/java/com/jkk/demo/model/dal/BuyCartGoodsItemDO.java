package com.jkk.demo.model.dal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BuyCartGoodsItemDO {
	@Data
	public static class goodsListItemDO{
		private Integer id;
		private Integer num;
	}

	private Integer storeId;

	private List<goodsListItemDO> goodsIdList;
}
