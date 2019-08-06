package com.lw.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.lw.blog.bean.Blog;
import com.lw.blog.dao.BlogDao;
import com.lw.blog.dao.mapper.BlogMapper;

public class BlogDaoImpl extends SqlSessionDaoSupport implements BlogDao{

	public void saveBlog(Blog blog, Map map) {
		this.getBlogMapper().saveBlog(blog);
	}
	
	public void deleteBlog(int blogId, int userId, Map map) {
		if(map!=null&&map.containsKey("realDelete")){
			Map inMap = new HashMap();
			Map<String,Integer> paramMap = new HashMap<String, Integer>();
			if(blogId>0)
				paramMap.put("blog_id", blogId);
			if(userId>0)
				paramMap.put("user_id", userId);
			inMap.put("paramMap", paramMap);
			this.getBlogMapper().deleteBlogByCondition(inMap);
		}else{
			this.getBlogMapper().deleteBlogById(blogId);
		}
		
	}

	public void updateBlog(Blog blog, Map map) {
		this.getBlogMapper().updateBlogById(blog);
	}

	public Blog findBlogById(int blogId, Map map) {
		return this.getBlogMapper().findBlogById(blogId);
	}

	public Blog findBlogByDate(int userId,Map map){
		return this.getBlogMapper().findBlogByDate(userId);
	}
	
	public List<Blog> findBlogByUserId(int userId, Map map) {
		return this.getBlogMapper().findBlogByUserId(userId);
	}

	public List<Blog> findBlogByLabel(List<String> labels, Map map) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Blog> findBlogByContent(String content, Map map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	private BlogMapper getBlogMapper(){
		return this.getSqlSession().getMapper(BlogMapper.class);
	}

}
