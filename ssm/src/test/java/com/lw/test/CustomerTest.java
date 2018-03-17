package com.lw.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lw.entity.Customer;
import com.lw.service.ICustomerService;

public class CustomerTest extends TestCase {

	@Test
	public void testAddCustomer(){
		ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
//		String names[] = act.getBeanDefinitionNames();
//		for(String name:names)
//		{
//			System.out.println(name);
//		}
		
		
		Customer customer = new Customer();
		customer.setCustomerId(1022);
		customer.setCustomerName("king1024");
		
		ICustomerService service = (ICustomerService) act.getBean("customerService");
		try {
			service.insert(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
