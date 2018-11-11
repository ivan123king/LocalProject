package com.lw;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="SERVICE-HELLOWORLD",fallback=HelloWorldFailService.class)
public interface HelloWorldService {
	@RequestMapping(value="/home",method=RequestMethod.GET)
	String sayHello();
}
