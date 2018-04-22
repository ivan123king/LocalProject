package com.lw.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.lw.user.service.CustomerService;

public class LogUtil {
	private static Logger log = Logger.getLogger(CustomerService.class.getSimpleName());
	static{
		log.setLevel(Level.ALL);
	}
	public static void writeLog(String msg,Level level){
		if(level==null) level = Level.INFO;
		log.log(level, msg);
	}
}
