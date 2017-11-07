package com.lw.hibernate;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lw.hibernate.relationship.n21_both.Customer;
import com.lw.hibernate.relationship.n21_both.Order;

public class HibernateTest {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void destroy(){
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testManyToOne(){
		Customer customer = (Customer) session.get(Customer.class, 1);
		customer.getOrders().size();
//		session.delete(customer);
		
//		Set<Order> orders = customer.getOrders();
//		System.out.println(orders.size());
		
//		Customer customer = new Customer();
//		customer.setCustomerName("King");
//		
//		Order order1 = new Order();
//		order1.setCustomer(customer);
//		order1.setOrderName("order1");
//		
//		Order order2 = new Order();
//		order2.setCustomer(customer);
//		order2.setOrderName("order1");
//		
//		customer.getOrders().add(order1);
//		customer.getOrders().add(order2);
//		
//		session.save(customer);
//		session.save(order1);
//		session.save(order2);
	}
	
	
	@Test
	public void test() {
		
//		Pay pay = new Pay();
//		pay.setMonthlyPay(1000);
//		pay.setYearPay(20);
//		
//		com.lw.hibernate.Work work = new com.lw.hibernate.Work();
//		work.setPay(pay);
//		session.save(work);
		
		
//		News news = new News("Java","king",new Date());
//		session.save(news);
//		News news = (News) session.get(News.class, 1);
//		System.out.println(news.getDesc());
		
		
//		session.update(news);
		
//		transaction.commit();
//		session.close();
//		
//		session = sessionFactory.openSession();
//		transaction = session.beginTransaction();
//		session.update(news);
		
//		session.doWork(new Work() {
//			@Override
//			public void execute(Connection conn) throws SQLException {
//				System.out.println(conn);
//			}
//		});
	}
	
	
}
