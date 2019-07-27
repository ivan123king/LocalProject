package com.lw.house.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lw.house.api.common.RestResponse;
import com.lw.house.api.service.UserService;

@RestController
public class ApiUserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="test/getusername")
	public RestResponse<String> getUserName(Long id){
		return RestResponse.success(userService.getUserName(id));
	}
}
