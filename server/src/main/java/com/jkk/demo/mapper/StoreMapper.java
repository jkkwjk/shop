package com.jkk.demo.mapper;

import com.jkk.demo.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreMapper extends JpaRepository<Store, Integer> {
	Boolean existsByUserId(Integer userId);

	Store findByUserId(Integer userId);
}
