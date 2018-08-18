package com.lw.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {

	public static final String ERROR_PAGE = "error";
	
	@ExceptionHandler(value=Exception.class)
	public Object errorHandler(HttpServletRequest req,HttpServletResponse resp,Exception e){
		ModelAndView mav = new ModelAndView();
		mav.addObject("reason",e);
		mav.addObject("url",req.getRequestURL());
		mav.setViewName(ERROR_PAGE);
		return mav;
	}
}
