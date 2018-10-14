package com.lw.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lw.house.common.model.User;
import com.lw.house.common.result.ResultMsg;
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
}
