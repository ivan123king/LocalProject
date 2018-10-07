package com.lw.house.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;


@Configuration
public class DruidConfig {

	/**
	 * @ConfigurationProperties的作用是将application.properties中spring.druid开头
	 * 的属性配置，自动加载到DruidDataSource类中
	 * @return
	 */
	@ConfigurationProperties(prefix="spring.druid")
	//init(),close()都是DruidDataSource中方法
	@Bean(initMethod="init",destroyMethod="close")
	public DruidDataSource dataSource(){
		DruidDataSource dataSource = new DruidDataSource();
		//将SQL监视器配置到池中
		dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
		return dataSource;
	}
	
	/**
	 * StatFilter Druid的统计监控信息，监视SQL执行状态
	 * @return
	 */
	@Bean
	public Filter statFilter(){
		StatFilter statFilter = new StatFilter();
		//5s的sql执行时间表示为慢SQL
		statFilter.setSlowSqlMillis(5000);
		//设置需要打印日志
		statFilter.setLogSlowSql(true);
		//日志合并
		statFilter.setMergeSql(true);
		return statFilter;
	}
	
	/**
	 * 对Servlet的/druid/下面的URL进行监控
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
	}
}
