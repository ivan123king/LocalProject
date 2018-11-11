package com.lw;

import org.springframework.stereotype.Component;

/**
 * 定义一个断路器处理类
 *
 */
@Component
public class HelloWorldFailService implements HelloWorldService{
	
	@Override
	public String sayHello() {
		return "remote service fail.";
	}

}
