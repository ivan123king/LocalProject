package com.lw.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lw.blog.bean.Blog;
import com.lw.blog.constinfo.ConstFile;
import com.lw.blog.dao.BlogDao;
import com.lw.blog.service.BlogService;
import com.lw.utils.LogUtil;
import com.lw.utils.StringUtil;
import com.mysql.jdbc.StringUtils;

@Service
public class BlogServiceImpl implements BlogService{

	private final static String className = BlogService.class.getName();
	
	@Autowired
	private BlogDao blogDao;
	
	public void saveBlog(Blog blog, Map map) {
		if(blog!=null){
			Date blogDate = blog.getBlogDate();
			if(blogDate==null) blog.setBlogDate(new Date());
			blogDao.saveBlog(blog, null);
		}
	}

	public List<Blog> findBlogByUserId(String userId) {
		int ruserId = 0;
		try{
			ruserId = Integer.parseInt(userId);
		}catch(NumberFormatException e){
			String msg = className+" 格式化查询UserId出错。UserId:"+userId;
			LogUtil.writeLog(msg, Level.SEVERE);
			return null;
		}
		return blogDao.findBlogByUserId(ruserId, null);
	}

	public void updateBlogById(Blog blog, Map map) {
		if(blog!=null){
			if(blog.getBlogDate()==null) blog.setBlogDate(new Date());
			String title = blog.getTitle();
			if(StringUtil.isEmpty(title)) blog.setTitle(ConstFile.BLOG_TITLE);
			blogDao.updateBlog(blog, map);
		}
	}

	public void deleteBlogByUserIdOrBlogId(int blogId, int userId, Map map) {
		if(userId>0){
			this.blogDao.deleteBlog(blogId, userId, map);
		}
	}

}
