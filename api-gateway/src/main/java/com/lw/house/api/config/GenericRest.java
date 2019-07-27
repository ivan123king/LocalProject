package com.lw.house.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class GenericRest {

	@Autowired
	private RestTemplate lbRestTemplate;//负载均衡的模板
	
	@Autowired
	private RestTemplate directRestTemplate;//直连请求模板
	
	private static final String directFlag = "direct://";
	
	/**
	 * 
	 * @param url
	 * @param reqBody
	 * @param ptr  用于反序列化
	 * @return
	 */
	public <T> ResponseEntity<T> post(String url,Object reqBody,ParameterizedTypeReference<T> ptr){
		RestTemplate template = getRestTemplate(url);
		url = url.replace(directFlag, "");
		return template.exchange(url, HttpMethod.POST,new HttpEntity(reqBody),ptr);
	}
	
	public <T> ResponseEntity<T> get(String url,ParameterizedTypeReference<T> ptr){
		RestTemplate template = getRestTemplate(url);
		url = url.replace(directFlag, "");
		return template.exchange(url, HttpMethod.GET,null,ptr);
	}
	
	/**
	 * 通过url判断是直连，还是负载均衡请求
	 * @param url
	 * @return
	 */
	private RestTemplate getRestTemplate(String url){
		if(url.contains(directFlag)){
			return directRestTemplate;
		}
		return lbRestTemplate;
	}
}
