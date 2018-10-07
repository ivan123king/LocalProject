package com.lw.house;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(value=SpringRunner.class)
@SpringBootTest
public class HouseApplicationTests {

	@Autowired
	private HttpClient httpClient;
	
	@Test
	public void testHttpClient() throws ClientProtocolException, IOException{
		HttpResponse response  = httpClient.execute(new HttpGet("http://www.baidu.com"));
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
	}
}
