package com.lw.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.assertj.core.util.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;

/**
 * druid连接池配置
 * @author lenovo
 *
 */
@Configuration
public class DruidConfig {
	
	@ConfigurationProperties(prefix="spring.druid")
	@Bean(initMethod="init",destroyMethod="close")
	public DataSource dataSource(Filter statFilter) throws SQLException{
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setProxyFilters(Lists.newArrayList(statFilter));
		return dataSource;
	}
	
	@Bean
	public Filter statFilter(){
		StatFilter filter = new StatFilter();
		filter.setSlowSqlMillis(5000);
		filter.setLogSlowSql(true);
		filter.setMergeSql(true);
		return filter;
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(){
		return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
	}
	
}
