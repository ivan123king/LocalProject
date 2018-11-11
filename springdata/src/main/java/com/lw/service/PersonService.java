package com.lw.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lw.entity.Person;
import com.lw.repository.PersonCrudRepository;
import com.lw.repository.PersonPagingAndSortingRepository;
import com.lw.repository.PersonRepository;

/**
 * 此处由beans-springdata.xml中配置的<context:component-scan base-package="com.lw.entity"/>
 * 扫描到，所以需要修改扫描的包到 com.lw
 * @author king
 *
 */
@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonCrudRepository personCrudRepository;
	
	@Autowired
	private PersonPagingAndSortingRepository personPagingAndSortingRepository;
	
	/*
	 * 设置事务才能够做update
	 */
	@Transactional
	public void updatePerson(int id,int age) {
		personRepository.update(id, age);
	}
	
	@Transactional
	public void save(List<Person> persons) {
		personCrudRepository.saveAll(persons);
	}
}
