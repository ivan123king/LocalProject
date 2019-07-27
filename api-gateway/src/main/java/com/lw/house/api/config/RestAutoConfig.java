package com.lw.house.api.config;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.http.client.HttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class RestAutoConfig {

	public static class RestTemplateConfig{
		
		@Bean
		@LoadBalanced//为了负载均衡  使用拦截器对ip:port替换，以找到合适的服务
		RestTemplate lbRestTemplate(HttpClient httpClient){
			//使用Factory来处理httpClient
			RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
			//设置响应的序列化编码
			template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			//设置响应的消息转换器
			template.getMessageConverters().add(1,new FastJsonHttpMessageConverter5());
			return template;
		}
		
		/**
		 * 用来测试直连某服务器的服务，而不是通过转发，所以要去掉@LoadBalanced
		 * @param httpClient
		 * @return
		 */
		@Bean
		RestTemplate directRestTemplate(HttpClient httpClient){
			//使用Factory来处理httpClient
			RestTemplate template = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
			//设置响应的序列化编码
			template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
			//设置响应的消息转换器
			template.getMessageConverters().add(1,new FastJsonHttpMessageConverter5());
			return template;
		}
	}
	
	/**
	 * 为了处理FastJsonHttpMessageConverter中使用MediaType.ALL导致spring
	 * 将使用字节流来处理字符串，而不是使用json格式，所以此处需要定制
	 * @author lenovo
	 *
	 */
	public  static class FastJsonHttpMessageConverter5 extends FastJsonHttpMessageConverter{

		static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
		
		FastJsonHttpMessageConverter5(){
			setDefaultCharset(DEFAULT_CHARSET);
			setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,new MediaType("application","*+json")));
			
		}
	}
}
