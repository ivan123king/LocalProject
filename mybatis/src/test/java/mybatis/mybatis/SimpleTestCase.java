package mybatis.mybatis;

import java.io.InputStream;
import java.sql.Connection;

import junit.framework.TestCase;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.lw.mapper.CustomerMapper;
import com.lw.pojo.Customer;

public class SimpleTestCase extends TestCase {

	@Test
	public void test() throws Exception{
		String resource = "mybatis_config.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CustomerMapper customerMapper = (CustomerMapper) sqlSession.getMapper(CustomerMapper.class);
		
		
		Connection conn = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
		SQL sql = new SQL();
		sql.SELECT("c.customer_id,c.customer_id");
		sql.SELECT("t.id as item_id,t.name as item_name");
		sql.FROM("customer c","items t");
		sql.WHERE("c.customer_id = t.id");
		sql.AND();
		sql.WHERE("c.customer_name = 'king'");
		System.out.println(sql.toString());
		
		
		Customer c = new Customer();
		
//		List list = new ArrayList();
//		list.add(4);
//		list.add(2);
//		list.add(9);
//		customerMapper.deleteByCustomerId(list);
		
//		Map inMap = new HashMap();
//		inMap.put("customer_name", "king03");
////		inMap.put("customer_id",13);
//		Map paramMap = new HashMap();
//		paramMap.put("inMap", inMap);
//		customerMapper.deleteByCondition(paramMap);
//		sqlSession.commit();
		
		
		
//		c.setCustomerName("xiumahe");
//		customerMapper.addNewCustomer(c);
		
//		Customer c2 = customerMapper.findByCustomerId(4);
//		c.setCustomerId(4);
//		c.setCustomerName("sadouxi4");
//		customerMapper.updateCustomerById(c);
//		sqlSession.commit();
//		c2 = customerMapper.findByCustomerId(4);
//		System.out.println(c2.getCustomerName());
		
		//		Customer02 customer = customerMapper.findByCustomer02Id(1);
//		Customer02 customer02 = customerMapper.findCustomerWithItem(1);
//		System.out.println(customer02.getItem().getName());
//		System.out.println(customer02.getItems().get(0).getName());
		
		//		List<Customer> customers = customerMapper.getAllCustomer();
//		System.out.println(customers.size());
//		if(customers!=null&&customers.size()>0){
//			for(Customer c:customers)
//				System.out.println(c.getCustomerName());
//		}
		
	}
	
}
