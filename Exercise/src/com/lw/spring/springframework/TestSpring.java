package com.lw.spring.springframework;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.lw.spring.springframework.aspect.proxy.CglibProxy;
import com.lw.spring.springframework.aspect.proxy.JdkProxy;
import com.lw.spring.springframework.aspect.proxy.UserDaoImpl;
import com.lw.spring.springframework.assemblage.User;
import com.lw.spring.springframework.assemblage.UserController;
import com.lw.spring.springframework.jdbc.CustomerDao;
import com.lw.spring.springframework.simple.UserDao;
import com.lw.spring.springframework.simple.UserService;

public class TestSpring {

	private final String xml = "applicationContext.xml";
	
	@Test
	public void testTransactionManager() throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		CustomerDao customerDao = (CustomerDao)context.getBean("customerDao");
		String sql = "insert into customer(customer_name) values(?)";
		Object[] params = new Object[]{"king007"};
		customerDao.operCustomer(sql, params);
	}
	
	@Test
	public void testJdbcTemplate(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		JdbcTemplate jdbcTemplate = (JdbcTemplate)context.getBean("jdbcTemplate");
		String sql = " insert into customer(customer_name) values(?) ";
		Object[] obj = new Object[]{"tacimei"};
		//返回受影响行数
		int num = jdbcTemplate.update(sql,obj);
		
		sql = "update customer c set c.customer_name = ? where c.id = ?";
		obj = new Object[]{"liuwei",7};
		//返回受影响行数
		num = jdbcTemplate.update(sql,obj);
		
		sql = "delete from customer where id=?";
		//返回受影响行数
		num = jdbcTemplate.update(sql,9);
		
		
	}
	
	@Test
	public void testAspectJByAnnotation(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		com.lw.spring.springframework.aspect.proxy.UserDao userDao = 
				(com.lw.spring.springframework.aspect.proxy.UserDao) context.getBean("userDao");
		try {
			userDao.addUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAspectJByXml(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		com.lw.spring.springframework.aspect.proxy.UserDao userDao = 
				(com.lw.spring.springframework.aspect.proxy.UserDao) context.getBean("userDao");
		try {
			userDao.addUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSpringAop(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		com.lw.spring.springframework.aspect.proxy.UserDao userDao = 
				(com.lw.spring.springframework.aspect.proxy.UserDao) context.getBean("userDaoProxy");
		userDao.addUser();
		userDao.deleteUser();
	}
	
	@Test
	public void testCglibProxy(){
		CglibProxy cglibProxy = new CglibProxy();
		UserDaoImpl udImpl = new UserDaoImpl();
		UserDaoImpl udImpl02 = (UserDaoImpl) cglibProxy.createProxy(udImpl);
		udImpl02.addUser();
		udImpl02.deleteUser();
	}
	
	@Test
	public void testJDKAspect(){
		//创建代理对象
		JdkProxy jdkProxy = new JdkProxy();
		//创建目标对象
		com.lw.spring.springframework.aspect.proxy.UserDao userDao = new UserDaoImpl();
		//获取代理对象
		com.lw.spring.springframework.aspect.proxy.UserDao userDao02 = (com.lw.spring.springframework.aspect.proxy.UserDao) jdkProxy.createProxy(userDao);
		//執行方法
		userDao02.addUser();
		userDao02.deleteUser();
	}

	
	@Test
	public void testAssemblageByAnnotation(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		UserController userController = (UserController)context.getBean("userController");
		userController.save();
	}
	
	@Test
	public void testAssembleByXML(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
		User user01 = (User)context.getBean("user1");
		User user02 = (User)context.getBean("user2");
		
		System.out.println(user01+"\r\n"+user02);
	}
	
	@Test
	public void testInitBean(){
		ApplicationContext context = new ClassPathXmlApplicationContext(xml);
//		UserService userService = (UserService)context.getBean("userService02");
		UserService userService = (UserService)context.getBean("userService03");
		System.out.println(userService);
		userService.say();
	}
	
	/**
	 * DI Dependency Injection 依赖注入
	 */
	@Test
	public void testDI(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService userService = (UserService) context.getBean("userService");
		userService.say();
	}

	/**
	 * IOC 控制反转
	 */
	@Test
	public void testIoc() {
		// 初始化spring容器，加载配置文件
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		// 通过容器获取userDao实例
		UserDao userDao = (UserDao) context.getBean("userDao");
		// 调用实例中方法测试是否正常初始化
		userDao.say();
	}
}
