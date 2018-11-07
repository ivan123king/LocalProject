package com.lw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lw.entity.Person;

public interface PersonJpaSpecificationExecutorRepository 
			extends JpaRepository<Person, Integer>,JpaSpecificationExecutor<Person>{

}
