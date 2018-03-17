package com.lw.controller.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * SpringMVC的自定义拦截器
 * 1.实现HandlerInterceptor接口
 * 2.在springmvc的配置文件中进行配置
 * 	  此处注意：拦截路径 <mvc:mapping path="/findUser.action"/> 
 * 		此findUser.action要和sumbitUser.jsp的请求路径一样，少了action都不行
 * 		这是不拦截路径： <mvc:exclude-mapping path=""/>
 * @author lenovo
 *
 */
public class CustomerInterceptor implements HandlerInterceptor{

	/**
	 * 整个请求完成后执行
	 */
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object handler, Exception arg3)
			throws Exception {
		System.out.println("请求完成执行！");
	}

	/**
	 * 控制器方法调用后，解析视图前执行
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		System.out.println("调用控制器后，解析视图前执行！");
	}
	
	/**
	 * 调用控制方法前执行
	 * 返回false拦截，true放行
	 */
	public boolean preHandle(HttpServletRequest req, HttpServletResponse arg1,
			Object handler) throws Exception {
		Enumeration<String> names =  req.getParameterNames();
		while(names.hasMoreElements()){
			System.out.println(names.nextElement());
			/*
			 *  customerId
				user.userName
			 */
		}
		String userName = req.getParameter("user.userName");
		System.out.println("用户名称："+userName);
		return true;
	}

}
