package com.lw.house.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import com.lw.house.api.config.GenericRest;

@Repository
public class UserDao {

	@Autowired
	private GenericRest rest;

	public String getUserName(Long id) {
		String url = "http://user/user/getusername?id=" + id;
		return rest.get(url, new ParameterizedTypeReference<String>() {
		}).getBody();
	}
}
