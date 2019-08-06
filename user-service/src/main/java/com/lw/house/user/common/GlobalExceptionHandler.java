package com.lw.house.user.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常处理器
 * 
 * @author lenovo
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(value=Throwable.class)
	@ResponseBody//异常信息变为返回的json格式
	public RestResponse<Object> handler(HttpServletRequest req,
			Throwable throwable) {
		LOGGER.error(throwable.getMessage(), throwable);
		RestCode restCode = Exception2CodeRepo.getCode(throwable);
		RestResponse<Object> response = new RestResponse<Object>(restCode.code,
				restCode.message);
		return response;
	}
}
