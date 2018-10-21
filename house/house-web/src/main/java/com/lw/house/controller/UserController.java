package com.lw.house.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.house.common.constants.CommonConstants;
import com.lw.house.common.model.User;
import com.lw.house.common.result.ResultMsg;
import com.lw.house.common.utils.HashUtils;
import com.lw.house.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("user")
	public List<User> getUsers() {
		return userService.getUsers();
	}

	/**
	 * 注册提交： 1.注册验证，2.发送邮件 3. 验证失败重定向到注册页面 注册页面获取： 根据account对象为空，或者其中name字段为空
	 * 
	 * @param account
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/accounts/register")
	public String accountsRegister(User account, ModelMap modelMap) {
		if (account == null || account.getName() == null) {// 注册页面获取
			return "/accounts/register";
		}
		// 用户验证
		ResultMsg resultMsg = UserHelper.validate(account);
		if (resultMsg.isSuccess() && userService.addAccount(account)) {
			modelMap.put("email", account.getEmail());
			return "/accounts/registerSubmit";
		} else {
			return "redirect:/accounts/register?" + resultMsg.asUrlParams();
		}
	}

	@RequestMapping(value = "/accounts/verify")
	public String verify(String key) {
		boolean result = userService.enable(key);
		if (result) {
			return "redirect:/index?"
					+ ResultMsg.successMsg("激活成功").asUrlParams();
		} else {
			return "redirect:/accounts/register?"
					+ ResultMsg.errorMsg("激活失敗，請確認鏈接是否過期？").asUrlParams();
		}
	}
	
	//-------------登录流程-------------
	/**
	 * 登录 
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/accounts/signin")
	public String signin(HttpServletRequest req){
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String target = req.getParameter("target");
		
		if(username==null||password==null){
			req.setAttribute("target", target);
			return "/accounts/signin";
		}
		
		User user = userService.auth(username,password);
		if(user==null){
			return "redirect:/accounts/signin?target="+target+"&username="+username+"&"+ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
		}else{
			//设置在Session中的值，freemarker可以获取到
			HttpSession session = req.getSession();
			session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
//			session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
			return StringUtils.isNoneBlank(target)?"redirect:"+target:"redirect:/index";
		}
	}
	
	/**
	 * 用户注销
	 * @param req
	 * @return
	 */
	@RequestMapping(value="accounts/logout")
	public String logout(HttpServletRequest req){
		HttpSession session = req.getSession();
		session.invalidate();
		return "redirect:/index";
	}
	
	//----------------个人信息------------------------
	/**
	 * 个人信息接口
	 * 1. 提供个人信息页面
	 * 2. 更新用户个人信息
	 * @param updateUser
	 * @param model
	 * @return
	 */
	@RequestMapping(value="accounts/profile")
	public String profile(HttpServletRequest req,User updateUser,ModelMap model){
		
		HttpSession sessionT = req.getSession();
		Enumeration<String> e = sessionT.getAttributeNames();
		while(e.hasMoreElements()){
			String key = e.nextElement();
			Object o = sessionT.getAttribute(key);
			System.out.println(key+":"+o);
		}
		
		if(updateUser.getEmail()==null){//用户为空说明是需要到个人信息页面
			return "/accounts/profile";
		}
		userService.updateUser(updateUser,updateUser.getEmail());
		User query = new User();
		query.setEmail(updateUser.getEmail());
		List<User> users = userService.getUserByQuery(query);
		
		//更新Session
		HttpSession session = req.getSession(true);
		session.setAttribute(CommonConstants.USER_ATTRIBUTE, users.get(0));
//		session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, users.get(0));
		return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功").asUrlParams();
	}
	
	/**
	 * 修改密码
	 * @param email
	 * @param password
	 * @param newPassword
	 * @param confirmPassword
	 * @param model
	 * @return
	 */
	@RequestMapping("accounts/changePassword")
	public String changePassword(String email,String password,String newPassword,String confirmPassword,ModelMap model){
		User user = userService.auth(email, password);
		if(user==null || !confirmPassword.equals(password)){
			return "redirect:/accounts/profile?"+ResultMsg.errorMsg("密码错误").asUrlParams();
		}
		User updateUser = new User();
		updateUser.setPasswd(HashUtils.encryPassword(newPassword));
		userService.updateUser(updateUser, email);
		return "redirect:/accounts/profile?"+ResultMsg.successMsg("更新成功").asUrlParams();
	}
	
}
