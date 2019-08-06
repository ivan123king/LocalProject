package com.lw.controller.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorHandler {

	public static final String ERROR_PAGE = "error";
	
	@ExceptionHandler(value=Exception.class)
	public Object errorHandler(HttpServletRequest request,HttpServletResponse response,Exception e){
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("reason",e);
//		mav.addObject("url",request.getRequestURL());
//		mav.setViewName(ERROR_PAGE);
		
		request.setAttribute("reason", e);
		request.setAttribute("url", request.getRequestURL());
		
		return "";
	}
}
