package com.lw.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lw.entity.Customer;
import com.lw.entity.User;
import com.lw.service.ICustomerService;

@Controller
public class CustomerController {

	@Autowired
	private ICustomerService cService;
	
	/**
	 * 如果想submitUser.jsp页面里面userName设置到Customer中User中的userName，那么input标签的name属性值
	 * 就必须要使用user.userName，第一个user对应Customer属性user，userName对应User类的userName
	 * @param customer
	 * @return
	 */
	@RequestMapping(value="/findUser")
	public String getUserFromCustomer(Customer customer){
		long customerId = customer.getCustomerId();
		User user = customer.getUser();
		System.out.println("客户ID："+customerId+" 用户名："+user.getUserName());
		return "customer";
	}
	/**
	 * 绑定pojo数据
	 * 此处的Customer类中customer必须要和addCustomer.jsp中input标签的name属性值一样才能配对
	 * @param cName
	 */
	@RequestMapping("/insertCustomer")
	public String addNewCustomer(Customer customer){
		String customerName = customer.getCustomerName();
		System.out.println("客户名称："+customerName);
		return "customer";
	}
	
	/**
	 * 简单的请求
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping("/findCustomerByName")
	public ModelAndView findCustomerById(String name,Model model){
		ModelAndView mav = new ModelAndView("customer");
		if(name!=null){
			List<Customer> customers = cService.findCustomerByName(name);
			model.addAttribute("customers",customers);
			mav.addObject("customers",customers);
		}
		return mav;
		
	}
	
	/**
	 * 重定向
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/redirect")
	public String redirectRequest(HttpServletRequest request,HttpServletResponse response
			,Model model){
		model.addAttribute("info","redirect");
		return "redirect:findCustomerByName";
	}
	
	/**
	 * 请求转发
	 * @return
	 */
	@RequestMapping(value="/forward")
	public String forwardRequest(Model model){
		model.addAttribute("info","forward");
		return "forward:findCustomerByName";
	}
}
