package com.lw.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 用于控制拦截器顺序等的类
 * 
 * @author lenovo
 *
 */
@SuppressWarnings("deprecation")
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthActionInterceptor authActionInterceptor;

	@Autowired
	private AuthInterceptor authInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//先添加的拦截器先执行
		//不拦截/static路径，拦截所有请求路径
		registry.addInterceptor(authInterceptor).excludePathPatterns("/static")
				.addPathPatterns("/**");
		registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");
		super.addInterceptors(registry);
	}

}
