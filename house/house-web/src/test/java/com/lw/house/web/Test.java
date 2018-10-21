package com.lw.house.web;

import static org.junit.Assert.*;

import com.lw.house.common.utils.HashUtils;

public class Test {

	@org.junit.Test
	public void test() {
		String hashpassword = HashUtils.encryPassword("123456");
		System.out.println(hashpassword);
	}

}
