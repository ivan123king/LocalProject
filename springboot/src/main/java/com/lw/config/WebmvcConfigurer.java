package com.lw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lw.interceptor.FirstInterceptor;
import com.lw.interceptor.SecondInterceptor;

@Configuration//配置拦截器
public class WebmvcConfigurer implements  WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		/**
		 * 拦截器按顺序执行
		 * 第一个拦截 /findcustomer下的所有url
		 * 第二个拦截 /findcustomer,/findallcustomer下的所有url
		 */
		registry.addInterceptor(new FirstInterceptor()).addPathPatterns("/findcustomer/**");
		registry.addInterceptor(new SecondInterceptor()).addPathPatterns("/findcustomer/**")
														.addPathPatterns("/findallcustomer/**");
	}
}
