package com.lw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lw.entity.Person;

public interface PersonJpaRepository extends JpaRepository<Person, Integer> {

}
