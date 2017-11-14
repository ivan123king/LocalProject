package com.lw.hibernate.hql.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
	public void testQBCFun(){
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.setProjection(Projections.max("salary"));//QBC聚合函数查询
		criteria.addOrder(Order.asc("salary"));//排序
		//分页
		int pageNo = 3;
		int pageSize = 5;
		criteria.setFirstResult((pageNo-1)*pageSize)
				.setMaxResults(pageSize);
		
		String sql = "";
		Query query = session.createSQLQuery(sql);
	}
	
	
	/**
	 * QBC的And和Or查询
	 */
	@Test
	public void testCriteriaAndOR(){
		Criteria criteria = session.createCriteria(Employee.class);
		//1.and查询  Conjunction本身类似于Criteria对象，其中可以添加Criteria对象
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("name", "%king"));
		Department dept = new Department();
		dept.setId(2);
		conjunction.add(Restrictions.eq("department", dept));
		System.out.println(conjunction);
		
		//2. Or查询
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.ge("salary", 1000f));
		disjunction.add(Restrictions.isNotNull("name"));
		
		criteria.add(conjunction);
		criteria.add(disjunction);
		criteria.list();
		
	}
	
	@Test
	public void testCriteria(){
		//1.创建Criteria 对象
		Criteria criteria = session.createCriteria(Employee.class);
		//2. 添加查询条件
		criteria.add(Restrictions.like("email", "18751836672%"));
		//3. 执行查询
//		Employee e = (Employee) criteria.uniqueResult();//得到唯一的数据
		List<Employee> emps = criteria.list();
		
	}
	
	@Test
	public void testLeftJoinFetch(){
		//通过此来连接employees表，返回Department的集合对象,distinct去除重复的 emps是Department类中的集合属性
		//注意： distinct后面的d字母是Department后面的d,不是随便命名的
		String hql = "select distinct d from Department d left join fetch d.emps";
		//left join 返回一个数组组成的集合，此数组由Department对象和Employee对象组成一条数据
		hql = " from Department d left join d.emps";
		//下面这条语句返回Department对象组成的集合
		hql = " select distinct d from Department d left join d.emps";
	}
	
	
	@Test
	public void testPropertyQuery(){
		String hql = "select e.email,e.salary from Employee e where e.department = :dept";
		hql = "select new Employee(e.email,e.salary) from Employee e where e.department = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(2);
		List<Employee> emps = query.setEntity("dept", dept).list();
		System.out.println(emps.get(0).getEmail());
		
//		List<Object[]> result = query.setEntity("dept",dept).list();
//		for(Object o[]:result){
//			System.out.println(Arrays.asList(o));
//		}
	}
	
	@Test
	public void testNamedQuery(){
		Query query = session.getNamedQuery("salaryEmps");
		List<Employee> emps = query.setFloat("minSal", 6000)
									.setFloat("maxSal", 10000)
									.list();
		
		query = session.getNamedQuery("emailQuery");
		emps = query.setString("email", "%18751836672%").list();
		
		System.out.println(emps.size());
		
	}
	
	@Test
	public void testPage(){
		//分页查询
		String hql = "from Employee";
		Query query = session.createQuery(hql);
		int pageNo = 3;//第几页
		int pageSize = 5;//每页多少条
		List<Employee> emps = query.setFirstResult((pageNo-1)*pageSize)
			 .setMaxResults(pageSize)
			 .list();
		System.out.println(emps.size());
	}
	
	@Test
	public void test() {
		//1.创建Query对象
//		String hql = "from Employee e where e.salary > ? "
//				+ "and e.email like ?";
//		Query query = session.createQuery(hql);
//		//2.绑定参数
//		query.setFloat(0, 600).setString(1,"%18751836672%");
//		//3.执行查询
//		List<Employee> emps = query.list();
//		System.out.println(emps.size());
		
//		String hql = "from Employee e where e.salary > :sal "
//				+ "and e.email like :email";
//		Query query = session.createQuery(hql);
//		//2.绑定参数
//		query.setFloat("sal", 600).setString("email","%18751836672%");
//		
		
		
	}
	
	
}
