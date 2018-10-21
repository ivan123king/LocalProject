package com.lw.house.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Joiner;
import com.lw.house.common.constants.CommonConstants;
import com.lw.house.common.model.User;

/**
 * 鉴权拦截器
 * @author lenovo
 *
 */
@Component
public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserContext.remove();
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Map<String,String[]> map = request.getParameterMap();
		map.forEach((key,value)->{
			if(key.equals("errorMsg")||key.equals("successMsg")||key.equals("target")){
				//将value数组之间使用 逗号 连接
				request.setAttribute(key,Joiner.on(",").join(value));
			}
		});
		
		String reqUrl = request.getRequestURI();
		if(reqUrl.startsWith("/static")||reqUrl.startsWith("/error")){
			return true;
		}
		//默认false ，为true表示没有session就创建一个
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(CommonConstants.USER_ATTRIBUTE);
		if(user!=null){
			UserContext.setUser(user);
		}
		return true;
	}

}
