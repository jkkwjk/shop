package com.jkk.demo.mapper;

import com.jkk.demo.model.dal.GoodsInfoDO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsInfoMapper extends JpaRepository<GoodsInfoDO, Integer> {
	@Query(value = "SELECT count(g_i.id) FROM `goods_info` as g_i,`store_info` as s_i WHERE g_i.id=?1 and s_i.id=g_i.store_id and s_i.user_id=?2 and g_i.goods_status = ?3", nativeQuery = true)
	Integer countByIdAndUserIdAndGoodsStatus(Integer id, Integer userId, Boolean goodsStatus);

	List<GoodsInfoDO> findAllByStoreIdAndGoodsStatus(Integer storeId, Boolean goodsStatus);

	GoodsInfoDO findByIdAndGoodsStatus(Integer id, Boolean goodsStatus);

	List<GoodsInfoDO> findAllByGoodsStatus(Boolean goodsStatus, Pageable pageable);

	Long countByGoodsStatus(Boolean goodsStatus);
}
