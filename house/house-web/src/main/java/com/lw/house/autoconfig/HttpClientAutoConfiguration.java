package com.lw.house.autoconfig;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 存在HttpClient这个类时才加载HttpClientAutoConfiguration
@ConditionalOnClass({ HttpClient.class })
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

	private final HttpClientProperties properties;

	public HttpClientAutoConfiguration(HttpClientProperties properties) {
		this.properties = properties;
	}

	@Bean
	// 不存在HttpClient时才加载这个方法
	@ConditionalOnMissingBean(HttpClient.class)
	public HttpClient httpClient() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(properties.getConnectTimeOut())
				.setSocketTimeout(properties.getSocketTimeOut()).build();
		HttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(requestConfig)
				.setUserAgent(properties.getAgent())
				.setMaxConnPerRoute(properties.getMaxConnPerRoute())
				.setMaxConnTotal(properties.getMaxConnTotal())
				//设置Httpclient不进行连接重试
				.setConnectionReuseStrategy(new NoConnectionReuseStrategy())
				.build();
		return client;
	}
}
