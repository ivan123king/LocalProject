package com.lw.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lw.blog.bean.Blog;
import com.lw.blog.constinfo.ConstFile;
import com.lw.blog.dao.BlogDao;
import com.lw.blog.service.BlogService;
import com.lw.user.bean.User;
import com.lw.user.dao.UserDao;
import com.lw.utils.LogUtil;
import com.lw.utils.StringUtil;

@Service
public class BlogServiceImpl implements BlogService{

	private final static String className = BlogService.class.getName();
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private UserDao userDao;
	
	public void saveBlog(Blog blog, Map map) {
		if(blog!=null){
			Date blogDate = blog.getBlogDate();
			if(blogDate==null) blog.setBlogDate(new Date());
			blogDao.saveBlog(blog, null);
		}
	}

	public List<Blog> findBlogByUserId(int userId) {
		List<Blog> blogList = new ArrayList<Blog>();
		try{
			blogList = blogDao.findBlogByUserId(userId, null);
			if(!CollectionUtils.isEmpty(blogList)){
				for(Blog b:blogList){
					int uId = b.getUserId();
					User user = userDao.findUserById(String.valueOf(uId));
					if(user!=null){
						b.setUserName(user.getUserName());
					}
				}
			}
		}catch(NumberFormatException e){
			String msg = className+" 格式化查询UserId出错。UserId:"+userId;
			LogUtil.writeLog(msg, Level.SEVERE);
		}
		return blogList;
	}
	
	public Blog findBlogByDate(int userId,Map map){
		int ruserId = 0;
		Blog blog = null;
		try{
			blog = blogDao.findBlogByDate(ruserId, null);
		}catch(NumberFormatException e){
			String msg = className+" 格式化查询UserId出错。UserId:"+userId;
			LogUtil.writeLog(msg, Level.SEVERE);
		}
		return blog;
	}

	public void updateBlogById(Blog blog, Map map) {
		if(blog!=null){
			blog.setBlogDate(new Date());
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
	
	public Blog findBlogById(int blogId){
		return blogDao.findBlogById(blogId, null);
	}

}
