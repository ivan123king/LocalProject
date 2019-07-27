package com.lw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.lw.house.api.config.NewRuleConfig;

@SpringBootApplication
/*
 * 在调用服务user的时候，使用负载均衡配置NewRuleConfig
 * 此处的user是user-service工程中配置的spring.application.name=user
 */
@RibbonClient(name="user",configuration=NewRuleConfig.class)
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}
