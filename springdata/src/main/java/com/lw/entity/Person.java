package com.lw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 1.开发完实体类后，springdata会自动建表
 * @author king
 *
 */
@Entity
@Table(name="person")//表名
public class Person {

	private Integer pId;
	private String name;
	private Integer age;
	
	@GeneratedValue//id自增
	@Id
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
	@Column(length=20)//设置列长度
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	
}
