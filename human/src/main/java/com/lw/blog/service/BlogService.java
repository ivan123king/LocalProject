package com.lw.blog.service;

import java.util.List;
import java.util.Map;

import com.lw.blog.bean.Blog;

public interface BlogService {

	public void saveBlog(Blog blog,Map map);
	public List<Blog> findBlogByUserId(String userId);
	public void updateBlogById(Blog blog,Map map);
	/**
	 * 逻辑删除 或者物理删除
	 * @param blogId
	 * @param userId
	 * @param map
	 */
	public void deleteBlogByUserIdOrBlogId(int blogId,int userId,Map map);
}
