package com.jkk.demo.mapper;

import com.jkk.demo.model.dal.OrderDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper extends JpaRepository<OrderDO, Integer> {
	List<OrderDO> findAllByStatusAndUserIdOrderByCreateTimeDesc(Integer status, Integer userId);

	OrderDO findByIdAndUserId(Integer id, Integer userId);
}
