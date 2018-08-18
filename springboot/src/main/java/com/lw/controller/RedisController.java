package com.lw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

	@Autowired
	private StringRedisTemplate redis;
	
	@RequestMapping(value="/getCache")
	public String getCache(){
		redis.opsForValue().set("key", "value");
		return redis.opsForValue().get("key");
	}
}
