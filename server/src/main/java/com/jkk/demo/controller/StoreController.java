package com.jkk.demo.controller;

import com.jkk.demo.aspect.ValidParam;
import com.jkk.demo.constant.SessionFiled;
import com.jkk.demo.model.Store;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.service.StoreService;
import com.jkk.demo.service.UserService;
import com.jkk.demo.util.RestfulRes;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("store")
public class StoreController {
	private final StoreService storeService;
	private final UserService userService;

	public StoreController(StoreService storeService, UserService userService) {
		this.storeService = storeService;
		this.userService = userService;
	}

	@ValidParam
	@PostMapping
	public RestfulRes<Store> addStore(@Length(min = 2, max = 10)String storeName,
	                           @Length(min = 5, max = 40)String storeDescribe,
	                           @SessionAttribute(SessionFiled.USER_DO)UserDO userDO){
		Store store = new Store();
		store.setUserId(userDO.getId());
		store.setCredit(0);
		store.setStoreName(storeName);
		store.setStoreDescribe(storeDescribe);
		return RestfulRes.success(storeService.saveStore(store));
	}

	@GetMapping
	public RestfulRes<Store> findStore(@SessionAttribute(SessionFiled.USER_DO) UserDO userDO){
		Store store = storeService.findStoreByUserId(userDO.getId());
		if (store == null){
			return RestfulRes.fail();
		}else {
			return RestfulRes.success(store);
		}
	}

	@GetMapping("get_owner_name")
	public RestfulRes<String> getOwnerName(Integer id){
		UserDO userDO = new UserDO();
		userDO.setId(storeService.findStoreById(id).getUserId());

		return RestfulRes.success(userService.getInfo(userDO).getNickName());
	}
}
