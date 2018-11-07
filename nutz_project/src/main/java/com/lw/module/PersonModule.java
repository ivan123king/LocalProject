package com.lw.module;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.util.cri.SqlExpressionGroup;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSONObject;
import com.lw.pojo.Person;
import com.lw.service.PersonService;

@IocBean
@At("/person")
@Ok("json")
@Fail("http:500")
public class PersonModule {

	@Inject
	protected Dao dao;
	
	@Inject(value="person_service")
	protected PersonService service;
	
	@At("/count")
	public Object count() {
		return this.service.count();
	}
	
	@At("/add")
	public Object addPerson(@Param("..")Person person,HttpSession session) {
		NutMap ret = new NutMap();
		Person personT = dao.fetch(Person.class,Cnd.where("name","=",person.getName()));
		if(personT!=null) {
			return ret.setv("retCode","fail").setv("retMsg", "exists this person");
		}
		dao.insert(person);
		session.setAttribute("personInfo", JSONObject.toJSONString(person));
		return ret.setv("retCode","success");
	}
	
	@At("/update")
	@AdaptBy(type=JsonAdaptor.class)
	/**
	 * json入参：
	 * 	json格式： 
		{
			"person":{
				"id":"1",
				"name":"king_lw",
				"age":"20"
			}
		}
	 * @param person
	 * @param session
	 * @return
	 */
	public Object updatePerson(@Param("person")Person person,HttpSession session) {
		NutMap ret = new NutMap();
		person.setName(null);//为空不更新
		int updateCount = dao.updateIgnoreNull(person);
		
		//获取新老Person信息
		Object personInfo = session.getAttribute("personInfo");
		String newPersonInfo = JSONObject.toJSONString(person);
		session.setAttribute("personInfo", newPersonInfo);
		Map<String,Object> infoMap = new HashMap<String,Object>();
		infoMap.put("updateCount",String.valueOf(updateCount));
		
		Map<String,Object> personMap = new HashMap<String,Object>();
		personMap.put("oldPerson", personInfo);
		personMap.put("newPerson", newPersonInfo);
		
		infoMap.put("personInfo",personMap);
		
		ret.setAll(infoMap);
		return ret;
	}
	
	@At("/listAllPerson")
	public QueryResult listAllPerson(int pageNo,int pageSize,Person person) {
		Pager pager = dao.createPager(pageNo, pageSize);
		Cnd condition = null;
		//设置查询条件
		if(person!=null) {
	    	SqlExpressionGroup sqlExpGroup = Cnd.exps("1","=","1");
	    	String name = person.getName();
	    	if(name!=null&&!"".equals(name)) {
	    		sqlExpGroup.and("p_name","LIKE",name);
	    	}
	    	int age = person.getAge();
	    	if(age>0) {
	    		sqlExpGroup.and("p_age","=",age);
	    	}
	    	Date birthday = person.getBirthday();
	    	if(birthday!=null) {
	    		sqlExpGroup.and("birthday","=",birthday);
	    	}
	    	condition = Cnd.where(sqlExpGroup);
	    }
		/*
		 * 利用反射将Person有值的字段变为对应SQL查询条件
		 * name和age对应数据库字段为 p_name,p_age，所以做转换
		 * PS： 这里弄复杂了，三行代码就能解决的事情却用了反射，目的是为了装B
		 * 	  装B失败，(String)f.get(person)这里如果是age那么就是Integer，无法转换为String报错
		 *   代码留着
		 */
//		Field[] fields = person.getClass().getDeclaredFields();
//		Field.setAccessible(fields, true);
//		if(fields!=null) {
//			for(Field f:fields) {
//				try {
//					String name = f.getName();
//					String value = (String) f.get(person);
//				} catch (IllegalArgumentException | IllegalAccessException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		//分页查询
		List<Person> personList = dao.query(Person.class, condition,pager);
		pager.setRecordCount(dao.count(Person.class,condition));
		QueryResult qr = new QueryResult(personList, pager);//返回分页查询结果和分页信息
		return qr;
		
		/**
		 * 返回：
		 * 有数据：
			 * {
				  "list": [
				    {
				      "id": 1,
				      "name": "king",
				      "age": 20,
				      "birthday": "2014-07-08 00:00:00"
				    }
				  ],
				  "pager": {
				    "pageNumber": 1,
				    "pageSize": 2,
				    "pageCount": 1,
				    "recordCount": 1
				  }
				}
			没数据：
				{
				  "list": [],
				  "pager": {
				    "pageNumber": 1,
				    "pageSize": 2,
				    "pageCount": 0,
				    "recordCount": 0
				  }
				}
		 */
		
	}
	
	@At("/redirect")
	@Ok(">>:/")//>> redirect , -> forward
	public void toIndex(HttpServletRequest req,HttpSession session) {
		String lang = req.getParameter("lang");
		if(lang!=null&&!"".equals(lang)) {
			Mvcs.setLocalizationKey(lang);
		}
		Map<String,Object> map = Mvcs.getLocaleMessage(Mvcs.getLocalizationKey());
		map.forEach((key,value)->{System.out.println(key+":"+value);});
	}
	
	@At("/forward")
	@Ok("->:/")//>> redirect , -> forward
	public void forwardIndex(HttpServletRequest req,HttpSession session) {
		String lang = req.getParameter("lang");
		if(lang!=null&&!"".equals(lang)) {
			Mvcs.setLocalizationKey(lang);
		}
		Map<String,Object> map = Mvcs.getLocaleMessage(Mvcs.getLocalizationKey());
		map.forEach((key,value)->{System.out.println(key+":"+value);});
	}
}
