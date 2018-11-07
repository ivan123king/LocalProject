package com.lw.repository;

import org.springframework.data.repository.CrudRepository;

import com.lw.entity.Person;

/*
 * CrudRepository 这个spring-data内置的repository就有增删改查的方法，所以不需要自己写特有的方法
 */
public interface PersonCrudRepository extends CrudRepository<Person, Integer>{

}
