package com.lw.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

	@Async
	public void doTask01() throws InterruptedException{
		long startTime = System.currentTimeMillis();
		Thread.sleep(2000L);
		long endTime = System.currentTimeMillis();
		System.out.println("doTask01's Time:"+(endTime-startTime));
	}
	
	@Async
	public void doTask02() throws InterruptedException{
		long startTime = System.currentTimeMillis();
		Thread.sleep(200L);
		long endTime = System.currentTimeMillis();
		System.out.println("doTask02's Time:"+(endTime-startTime));
	}
	
	@Async
	public void doTask03() throws InterruptedException{
		long startTime = System.currentTimeMillis();
		Thread.sleep(600L);
		long endTime = System.currentTimeMillis();
		System.out.println("doTask03's Time:"+(endTime-startTime));
	}
}
