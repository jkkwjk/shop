package com.jkk.demo.service.impl;

import com.jkk.demo.mapper.UserMapper;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	private final UserMapper userMapper;
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public UserDO login(UserDO userDO) {
		UserDO res = userMapper.findByNickNameAndPwd(userDO.getNickName(), userDO.getPwd());
		if (res == null){
			res = userMapper.findByTelephoneAndPwd(userDO.getNickName(), userDO.getPwd()); //这里的nickname其实是telephone
		}

		if (res != null){
			res.setLastLoginTime(new Date());
			userMapper.save(res);
		}
		return res;
	}

	@Override
	public UserDO register(UserDO userDO) {
		userDO.setLastLoginTime(new Date());
		return userMapper.save(userDO);
	}

	@Override
	public UserDO modify(UserDO userDO) {
		return userMapper.save(userDO);
	}

	@Override
	public UserDO getInfo(UserDO userDO) {
		Optional<UserDO> userDOOptional = userMapper.findById(userDO.getId());
		Assert.isTrue(userDOOptional.isPresent(), "找不到用户信息: "+userDO);
		return userDOOptional.get();
	}
}
