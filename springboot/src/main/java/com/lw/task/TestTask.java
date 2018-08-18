package com.lw.task;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {
	
	//每过一秒执行一次
	@Scheduled(fixedRate=1000)
	public void schedule(){
		System.out.println(new Date().getSeconds());
	}
}
