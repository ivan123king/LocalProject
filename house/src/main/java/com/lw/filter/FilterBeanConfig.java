package com.lw.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 此处是用创建spring boot方式的拦截Bean
 * @author lenovo
 *
 */
@Configuration
public class FilterBeanConfig {

	/**
	 * @Bean注释是为了让这个方法返回的对象成为spring bean
	 * @return
	 */
	@Bean
	public FilterRegistrationBean logFilter(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		//设置过滤器
		filterRegistrationBean.setFilter(new LogFilter());
		List<String> urlList = new ArrayList<>();
		//设置过滤器拦截的URL，以下拦截所有URL
		urlList.add("*");
		filterRegistrationBean.setUrlPatterns(urlList);
		return filterRegistrationBean;
	}
}
