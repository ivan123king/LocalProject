package com.lw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lw.task.AsyncTask;

@RestController
public class AsyncController {
	
	@Autowired
	private AsyncTask asyncTask;

	@RequestMapping(value="/doAsync")
	public String doAsync() throws InterruptedException{
		long startTime = System.currentTimeMillis();
		asyncTask.doTask01();
		asyncTask.doTask02();
		asyncTask.doTask03();
		long endTime = System.currentTimeMillis();
		return "time spend "+(endTime-startTime);
	}
}
