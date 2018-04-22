package com.lw.blog.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.lw.blog.bean.Blog;

public class BlogServiceTest {

	ApplicationContext context = null;
	BlogService blogService = null;
	@Before
	public void before(){
		System.out.println("in");
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		blogService = context.getBean(BlogService.class);
	}
	
	@Test
	public void testSaveBlog() {
		Blog blog = new Blog();
//		blog.setUserId(3);
		blog.setContent("那年春天，春暖花开，心花怒放");
		BlogService blogService = context.getBean(BlogService.class);
		blogService.saveBlog(blog, null);
		
	}

	@Test
	public void testFindBlogByUserId() {
		
		List<Blog> blog = blogService.findBlogByUserId("3");
		if(blog!=null){
			for(Blog b:blog){
				System.out.println(b.getContent());
			}
		}
	}
	
	
	@Test
	public void testUpdateBlogById(){
		Blog blog = new Blog();
		blog.setBlogId(3);
		blog.setContent("let's go ");
		blog.setTitle("ready go对对对");
		blogService.updateBlogById(blog, null);
	}
	
	@Test
	public void testDeleteBlogById(){
		blogService.deleteBlogByUserIdOrBlogId(2, 2,null);
	}

	
	@Test
	public void testMap2Json(){
		Map map = new HashMap();
		 map.put("own", "own");
		 map.put("you", "you");
		 map.put("his", "his");
		 JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
		 String jsonStr = jsonObject.toString();
		 System.out.println(jsonStr);
		 
		 Blog blog = new Blog();
		 blog.setUserId(3);
		 blog.setBlogDate(new Date());
		blog.setContent("那年春天，春暖花开，心花怒放");
		jsonStr = JSONObject.toJSONString(blog);
		System.out.println(jsonStr);
	}
}
