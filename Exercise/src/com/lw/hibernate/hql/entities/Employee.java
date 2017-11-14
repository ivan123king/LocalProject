package com.lw.hibernate.hql.entities;

import javax.persistence.QueryHint;

public class Employee {
	
	private Integer id;
	private String name;
	private float salary;
	private String email;
	
	private Department department;
	
	public Employee(){}
	public Employee(String email,float salary){
		super();
		this.email = email;
		this.salary = salary;
	}
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

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
