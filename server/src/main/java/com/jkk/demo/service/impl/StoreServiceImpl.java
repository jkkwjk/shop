package com.jkk.demo.service.impl;

import com.jkk.demo.mapper.StoreMapper;
import com.jkk.demo.model.Store;
import com.jkk.demo.service.StoreService;
import com.jkk.demo.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {
	private final StoreMapper storeMapper;

	public StoreServiceImpl(StoreMapper storeMapper) {
		this.storeMapper = storeMapper;
	}

	@Override
	public Store saveStore(Store store) {
		return storeMapper.save(store);
	}

	@Override
	public Boolean existStoreByUserId(Integer userId) {
		return storeMapper.existsByUserId(userId);
	}

	@Override
	public Store modifyStore(Store store, Integer userId) {
		boolean safe = false;
		Store storeInDB = storeMapper.findByUserId(userId);
		if (storeInDB != null){
			safe = storeInDB.getUserId().equals(userId);
		}

		if (safe){
			BeanUtil.copyNullProperties(store, storeInDB); //noTest
			return storeMapper.save(storeInDB);
		}else {
			return null;
		}
	}

	@Override
	public Store findStoreByUserId(Integer userId) {
		return storeMapper.findByUserId(userId);
	}

	@Override
	public Store findStoreById(Integer id) {
		return storeMapper.findById(id).orElseThrow(() -> new IllegalArgumentException("找不到该店铺 -> "+id));
	}
}
