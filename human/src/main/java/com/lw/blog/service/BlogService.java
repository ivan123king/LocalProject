package com.lw.blog.service;

import java.util.List;
import java.util.Map;

import com.lw.blog.bean.Blog;

public interface BlogService {

	public void saveBlog(Blog blog,Map map);
	public Blog findBlogById(int blogId);
	public List<Blog> findBlogByUserId(int userId);
	/**
	 * 查询用户最近插入的一条博客记录
	 * @param userId
	 * @param map
	 * @return
	 */
	public Blog findBlogByDate(int userId,Map map);
	public void updateBlogById(Blog blog,Map map);
	/**
	 * 逻辑删除 或者物理删除
	 * @param blogId
	 * @param userId
	 * @param map
	 */
	public void deleteBlogByUserIdOrBlogId(int blogId,int userId,Map map);
}
