package com.lw.house.web.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lw.house.common.model.User;

/**
 * 拦截登录方面的请求
 * @author lenovo
 *
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
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
		User user = UserContext.getUser();//从全局线程变量中获取用户对象
		if(user==null){
			String msg = URLEncoder.encode("请先登录","utf-8");
			String target = URLEncoder.encode(request.getRequestURL().toString(),"utf-8");
			if("GET".equalsIgnoreCase(request.getMethod())){
				response.sendRedirect("/accounts/signin?errorMsg="+msg+"&target="+target);
			}else{//此处默认是POST请求
				response.sendRedirect("/accounts/signin?errorMsg="+msg);
			}
		}
		return true;
	}

}
