package com.lw.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.lw.entity.Person;

/*
 * PagingAndSortingRepository 分页和排序的
 */
public interface PersonPagingAndSortingRepository extends PagingAndSortingRepository<Person, Integer>{

}
