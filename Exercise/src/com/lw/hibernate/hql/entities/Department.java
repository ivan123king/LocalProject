package com.lw.hibernate.hql.entities;

import java.util.HashSet;
import java.util.Set;

public class Department {

	private Integer id;
	private String name;
	private Set<Employee> emps = new HashSet<Employee>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Employee> getEmps() {
		return emps;
	}
	public void setEmps(Set<Employee> emps) {
		this.emps = emps;
	}
			

}
