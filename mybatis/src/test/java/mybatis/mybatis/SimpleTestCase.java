package mybatis.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.lw.mapper.CustomerMapper;
import com.lw.pojo.Customer;

import junit.framework.TestCase;

public class SimpleTestCase extends TestCase {

	@Test
	public void test() throws Exception{
		String resource = "mybatis_config.xml";
		InputStream is = Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		CustomerMapper customerMapper = (CustomerMapper) sqlSession.getMapper(CustomerMapper.class);
		Customer customer = customerMapper.findByCustomerId(1);
		System.out.println(customer);
		
		//		List<Customer> customers = customerMapper.getAllCustomer();
//		System.out.println(customers.size());
//		if(customers!=null&&customers.size()>0){
//			for(Customer c:customers)
//				System.out.println(c.getCustomerName());
//		}
		
	}
	
}
