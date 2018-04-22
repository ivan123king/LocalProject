package com.lw.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.midi.MidiDevice.Info;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lw.user.bean.Customer;
import com.lw.user.bean.User;
import com.lw.user.dao.CustomerDao;
import com.lw.user.service.CustomerService;
import com.lw.utils.InfoC;
import com.lw.utils.LogUtil;
import com.lw.utils.StringUtil;


@Service
public class CustomerServiceImpl implements CustomerService {

	private static final String className = CustomerService.class.getName();
	
	@Autowired
	public CustomerDao customerDao;

	public int register(Customer customer,Map map) {
		String ID = customer.getID();
		List<Customer> custList = customerDao.findCustomerWithID(ID,null);
		if(custList!=null&&custList.size()==1){
			customerDao.addNewCustomer(customer,null);
			String password = (String) map.get("password");
			
			Customer newCustomer = custList.get(0);
			User user = new User();
			user.setCustomerId(newCustomer.getCustomerId());
			user.setUserName(newCustomer.getCustomerName());
			user.setPassword(password);
			customerDao.addNewUser(user, null);
			return InfoC.returnInfo.success.ordinal();
		}else{
			String msg = className+",此身份证号["+ID+"]已经注册。";
			LogUtil.writeLog(msg,Level.SEVERE);
			return InfoC.returnInfo.fail.ordinal();
		}
		
	}

	public User login(String name, String password, Map map) {
		if(StringUtil.isNotEmpty(name,password)){
			List<User> userList = customerDao.findUserByNameAndPassword(name, password, null);
			if(userList!=null&&userList.size()>0)
				return userList.get(0);
		}
		String msg = className+",用户"+name+"未找到，登录失败。";
		LogUtil.writeLog(msg,Level.SEVERE);
		return null;
	}

}
