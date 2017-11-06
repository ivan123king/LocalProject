package com.lw.hibernate;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void test() {
		
		Pay pay = new Pay();
		pay.setMonthlyPay(1000);
		pay.setYearPay(20);
		
		com.lw.hibernate.Work work = new com.lw.hibernate.Work();
		work.setPay(pay);
		session.save(work);
		
		
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
