package com.lw.hibernate.relationship.n2n_both;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.SessionFactoryBuilder;
import org.hibernate.metamodel.source.internal.SessionFactoryBuilderImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ManyToManyTest {
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {

		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

	}

	@After
	public void destroy() {
		transaction.commit();
		session.close();
		sessionFactory.close();
	}

	@Test
	public void testGet(){
		Item item = (Item) session.get(Item.class, 1);
		item.getCategories().size();
		
//		Category category = (Category) session.get(Category.class, 1);
//		category.getItems().size();
//		category.getName();
	}
	
	@Test
	public void test() {
		Category c1 = new Category();
		c1.setName("c01");
		
		Category c2 = new Category();
		c2.setName("c02");
		
		Item i01 = new Item();
		i01.setName("i01");
		
		Item i02 = new Item();
		i02.setName("i02");
		
		Item i03 = new Item();
		i03.setName("i03");
		
		i01.getCategories().add(c1);
//		i01.getCategories().add(c2);
		i02.getCategories().add(c1);
		i03.getCategories().add(c2);
		
		c1.getItems().add(i01);
		c1.getItems().add(i02);
		
		c2.getItems().add(i01);
		c2.getItems().add(i03);

		session.save(i01);
		session.save(i02);
		session.save(c2);
		session.save(i03);
		session.save(c1);
		
		//9条insert语句  categroies表2条，items表3条  中间表4条
	}

}
