package com.lw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * 获取hello内容的Service
 * @author king
 */
@Service
public class HelloService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="serviceFailure")
	public String getHelloContent() {
		/*
		 *  restTemplate.getForObject方法会通过ribbon负载均衡机制， 自动选择一个Hello word服务
		 */
		return restTemplate.getForObject("http://SERVICE-HELLOWORLD/home", String.class);
	}
	
	public String serviceFailure() {
		return "from ribbon project SERVICE-HELLOWORLD service fail. ";
	}
}
