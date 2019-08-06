package com.lw.blog.dao.mapper;

import java.util.List;
import java.util.Map;

import com.lw.blog.bean.Blog;

public interface BlogMapper {

	public void saveBlog(Blog blog);
	
	public List<Blog> findBlogByUserId(int userId);
	
	public Blog findBlogByDate(int userId);
	
	public Blog findBlogById(int blogId);
	
	public void updateBlogById(Blog blog);
	
	/**
	 * 物理删除
	 * @param inMap
	 */
	public void deleteBlogByCondition(Map<String,Object> inMap);
	
	/**
	 * 逻辑删除
	 * @param blogId
	 */
	public void deleteBlogById(int blogId);
}
