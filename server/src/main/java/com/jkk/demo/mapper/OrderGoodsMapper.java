package com.jkk.demo.mapper;

import com.jkk.demo.model.dal.OrderGoodsDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderGoodsMapper extends JpaRepository<OrderGoodsDO, Integer> {
	List<OrderGoodsDO> findAllByOrderIdOrderByStoreId(Integer orderId);

	OrderGoodsDO findByOrderIdAndGoodsId(Integer orderId, Integer goodsId);
}
