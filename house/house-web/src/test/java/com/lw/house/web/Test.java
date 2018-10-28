package com.lw.house.web;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.lw.house.common.utils.HashUtils;

@RunWith(value=SpringRunner.class)
//使用此注解表示要加载application.properties文件，同时要有spring boot的启动类
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT/*随机端口启动*/)
public class Test {

	@org.junit.Test
	public void test() {
		String hashpassword = HashUtils.encryPassword("123456");
		System.out.println(hashpassword);
	}

}
