package com.lw.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lw.user.bean.Customer;
import com.lw.user.bean.User;
import com.lw.user.service.CustomerService;
import com.lw.utils.DateDeal;
import com.lw.utils.InfoC;
import com.lw.utils.LogUtil;

@Controller
@RequestMapping(value="/users")
public class UserDealC {
	
	@Autowired
	public CustomerService customerService;

	@RequestMapping(value="register",method=RequestMethod.POST)
	public String register(HttpServletRequest request,Model model){
		String customerName = request.getParameter("customerName");
		String password = request.getParameter("password");
		String ID = request.getParameter("ID");
		String birthday = request.getParameter("birthday");
		String age = request.getParameter("age");
		String address = request.getParameter("address");
		
		Customer customer = new Customer();
		customer.setAddress(address);
		customer.setCustomerName(customerName);
		customer.setID(ID);
		customer.setBirthday(DateDeal.formatString2Date(birthday, "YYYY-MM-DD"));
		customer.setAge(Integer.parseInt(age));
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("password", password);
		int isSuccess = customerService.register(customer,map);
		if(isSuccess==InfoC.returnInfo.fail.ordinal()){
			String msg =  "注册用户失败，证件号【"+ID+"】，可能原因是此证件号已经被注册过。";
			model.addAttribute("info",msg);
			return "error";
		}
		return "users/login";
		
	}
	
	/**
	 * 登录
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletRequest request,Model model){
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		User user = customerService.login(name, password, null);
		if(user==null){
			model.addAttribute("info","用户"+name+"登录失败。");
			return "error";
		}else{
			HttpSession session = request.getSession();
			session.setAttribute("username", name);
			session.setAttribute("userId", user.getUserId());
			session.setAttribute("customerId", user.getCustomerId());
			return "index";
		}
		
	}
	
	
}
