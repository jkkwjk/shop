package com.jkk.demo.mapper;

import com.jkk.demo.model.dal.GoodsPropertyDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsPropertyMapper extends JpaRepository<GoodsPropertyDO, Integer> {
	List<GoodsPropertyDO> findAllByGoodsId(Integer goodsId);
}
