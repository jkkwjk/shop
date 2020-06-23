package com.jkk.demo.service;


import com.jkk.demo.model.dal.UserDO;

public interface UserService {
	UserDO login(UserDO userDO);

	UserDO register(UserDO userDO);

	UserDO modify(UserDO userDO);

	UserDO getInfo(UserDO userDO);
}
