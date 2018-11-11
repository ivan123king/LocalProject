package com.lw.service;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;

import com.lw.pojo.Person;

@IocBean(name="person_service")
public class PersonService {

	@Inject
	private Dao dao;
	
	public Object count() {
		NutMap ret = new NutMap();
		ret.setv("total", dao.count(Person.class));
		return ret;
	}
}
