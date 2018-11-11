package com.lw.jdbc;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.lw.entity.Person;
import com.lw.repository.PersonJpaRepository;
import com.lw.repository.PersonJpaSpecificationExecutorRepository;
import com.lw.repository.PersonPagingAndSortingRepository;
import com.lw.repository.PersonRepository;
import com.lw.service.PersonService;

public class SpringDataTest {

	ApplicationContext ctx = null;
	PersonRepository personRepository = null;
	PersonService personService;
	PersonPagingAndSortingRepository personPagingAndSortingRepository;
	PersonJpaRepository personJpaRepository;
	PersonJpaSpecificationExecutorRepository personExe;
	
	
	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("beans-springdata.xml");
//		personRepository = ctx.getBean(PersonRepository.class);
		personService = ctx.getBean(PersonService.class);
		personPagingAndSortingRepository = ctx.getBean(PersonPagingAndSortingRepository.class);
		personJpaRepository = ctx.getBean(PersonJpaRepository.class);
		personExe = ctx.getBean(PersonJpaSpecificationExecutorRepository.class);
	}
	
	@After
	public void end() {
		ctx = null;
		personRepository = null;
		personService = null;
		personPagingAndSortingRepository = null;
		personJpaRepository = null;
	}
	
	@Test
	public void jpaSpecificationTest() {
		Pageable pageable = PageRequest.of(1, 3);
		
		Specification<Person> specification = new Specification<Person>() {

			@Override
			public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				//构成条件   age>50
				Path path = root.get("age");
				Predicate predicate = criteriaBuilder.gt(path, 50);
				return predicate;
			}
		};
		//specification 查询条件，pageable 分页信息
		Page<Person> page = personExe.findAll(specification,pageable);
		System.out.println(String.format("总页数:%d,总记录数：%d,当前第几页：%d,当前页面的记录数：%d", 
				page.getTotalPages(),page.getTotalElements(),page.getNumber(),page.getNumberOfElements()));
		List<Person> persons = page.getContent();
		persons.stream().forEach((person)->{
			System.out.println(person.getpId()+":"+person.getName());
		});
	}
	
	@Test
	public void jpaTest() {
//		Optional<Person> optional = personJpaRepository.findById(3);
//		Person person = optional.get();
		
		Person person = new Person();
		person.setName("king");
		Example<Person> example = Example.of(person);
		Optional<Person> optional = personJpaRepository.findOne(example);
		person = optional.get();
		System.out.println(person.getpId());
	}
	
	@Test
	public void test() {
//		Person person = personRepository.findByName("king");
//		Person person = personRepository.getPersonById();
//		Person person = personRepository.queryByParams("king", 22).get(0);
//		Person person = personRepository.queryByParams02("king", 22).get(0);
//		Person person = personRepository.queryLikeName("k").get(0);
//		Person person = personRepository.queryLikeName02("k").get(0);
//		System.out.println(String.format("id:%d,name:%s,age:%d", person.getpId(),person.getName(),person.getAge()));
//		System.out.println(personRepository.getCount());
//		personService.updatePerson(3, 24);
		
//		List<Person> persons = new ArrayList<>();
//		for(int i=4;i<8;i++) {
//			Person p = new Person();
//			p.setAge(i);
//			p.setName("king_"+i);
////			p.setpId(i);
//			persons.add(p);
//			
//		}
//		personService.save(persons);
		
		/*
		 * 分页查询
		 * 1 是第二页  从0开始
		 * 3 是 size每页展示多少条
		 */
//		Pageable pageable = PageRequest.of(1, 3);
//		Page<Person> page = personPagingAndSortingRepository.findAll(pageable);
//		System.out.println(String.format("总页数:%d,总记录数：%d,当前第几页：%d,当前页面的记录数：%d", 
//				page.getTotalPages(),page.getTotalElements(),page.getNumber(),page.getNumberOfElements()));
//		List<Person> persons = page.getContent();
//		persons.stream().forEach((person)->{
//			System.out.println(person.getpId()+":"+person.getName());
//		});
		
		/*
		 * 排序
		 */
		Order order = new Order(Sort.Direction.DESC,"pId");
		Order order2 = new Order(Sort.Direction.DESC,"age");
		
		//方式一
		Sort sort = new Sort(order,order2);
		//方式二
		Sort.by(order,order2);
		
		Pageable pageable = PageRequest.of(1, 3,Sort.by(order,order2));
		Page<Person> page = personPagingAndSortingRepository.findAll(pageable);
		System.out.println(String.format("总页数:%d,总记录数：%d,当前第几页：%d,当前页面的记录数：%d", 
				page.getTotalPages(),page.getTotalElements(),page.getNumber(),page.getNumberOfElements()));
		List<Person> persons = page.getContent();
		persons.stream().forEach((person)->{
			System.out.println(person.getpId()+":"+person.getName());
		});
		
	}

}
