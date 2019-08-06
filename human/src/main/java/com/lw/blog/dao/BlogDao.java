package com.lw.blog.dao;

import java.util.List;
import java.util.Map;

import com.lw.blog.bean.Blog;

public interface BlogDao {
	
	public void saveBlog(Blog blog,Map map);
	/**
	 * 逻辑删除
	 * @param blogId
	 * @param userId
	 * @param map
	 */
	public void deleteBlog(int blogId,int userId,Map map);
	public void updateBlog(Blog blog,Map map);
	public Blog findBlogById(int blogId,Map map);
	public List<Blog> findBlogByUserId(int userId,Map map);
	/**
	 * 查找用户最近插入的一条博客记录
	 * @param userId
	 * @param map
	 * @return
	 */
	public Blog findBlogByDate(int userId,Map map);
	public List<Blog> findBlogByLabel(List<String> labels,Map map) ;
	public List<Blog> findBlogByContent(String content,Map map);
}
