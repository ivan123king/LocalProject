package com.lw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lw.house.user.common.RestResponse;

@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	

	@RequestMapping(value="/user/getusername")
	public RestResponse<String> getUserName(Long id){
		logger.info("incoming request");
		return RestResponse.success("test-user");
	}

}
