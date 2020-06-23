package com.jkk.demo.service;

import com.jkk.demo.model.Store;

public interface StoreService {
	Store saveStore(Store store);

	Boolean existStoreByUserId(Integer userId);

	Store modifyStore(Store store, Integer userId);

	Store findStoreByUserId(Integer userId);

	Store findStoreById(Integer id);
}
