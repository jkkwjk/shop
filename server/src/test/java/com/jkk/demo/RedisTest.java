package com.jkk.demo;

import com.jkk.demo.model.dal.BuyCartGoodsItemDO;
import com.jkk.demo.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RedisTest {
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ValueOperations<String, Object> valueOperations;



}
