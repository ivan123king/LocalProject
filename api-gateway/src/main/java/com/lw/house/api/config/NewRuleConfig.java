package com.lw.house.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

public class NewRuleConfig {

	@Autowired
	private IClientConfig ribbonClientConfig;
	
	/**
	 * 用来ping  /health地址，查询是否服务接通
	 * PingUrl 第一个参数设置为false是表示不需要安全检查
	 * @param config
	 * @return
	 */
	@Bean
	public IPing ribbonPing(IClientConfig config){
		return new PingUrl(false,"/health");
	}
	
	/**
	 * 定义负载均衡规则
	 */
	@Bean
	public IRule ribbonRule(IClientConfig config){
		//智能选择，选择最近服务成功的调用
		return new AvailabilityFilteringRule();
	}
}
