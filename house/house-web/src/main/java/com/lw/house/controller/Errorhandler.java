package com.lw.house.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 自定义异常处理
 * @author lenovo
 *
 */
@ControllerAdvice
public class Errorhandler {

	private static final Logger logger = LoggerFactory.getLogger(Errorhandler.class);
	
	@ExceptionHandler(value={Exception.class,RuntimeException.class})
	public String error500(HttpServletRequest request,Exception e){
		logger.error(e.getMessage(),e);
		logger.error(request.getRequestURL()+ "发生500错误。");
		return "error/500";
	}
}
