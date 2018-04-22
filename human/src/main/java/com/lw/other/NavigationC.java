package com.lw.other;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 导航到页面的Controller类
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value="/navigation")
public class NavigationC {

	/**
	 * 前往注册页面
	 * @return
	 */
	@RequestMapping(value="/register")
	public String toRegister(){
		return "users/register";
	}
	
	/**
	 * 前往登录页面
	 * @return
	 */
	@RequestMapping(value="/login")
	public String toLogin(){
		return "users/login";
	}
	
	/**
	 * 前往写博客页面
	 * @return
	 */
	@RequestMapping(value="/writeblog")
	public String towriteBlog(){
		return "blog/writeblog";
	}
	
	@RequestMapping(value="/bloglist")
	public String toBlogList(){
		return "redirect:/blog/listblog";
	}
	
}
