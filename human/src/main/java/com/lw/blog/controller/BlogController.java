package com.lw.blog.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lw.blog.bean.Blog;
import com.lw.blog.service.BlogService;
import com.lw.utils.LogUtil;

@Controller
@RequestMapping(value="/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	private final static String className = BlogController.class.getName();
	
	@RequestMapping(value="/writeblog")
	public String writeBlog(HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			request.setCharacterEncoding("UTF-8");
			String blogContent = request.getParameter("content");
			String userId = request.getParameter("userId");
			String title = request.getParameter("title");
			Blog blog = new Blog();
			blog.setContent(blogContent);
			blog.setBlogDate(new Date());
			blog.setTitle(title);
			try{
				blog.setUserId(Integer.parseInt(userId));
			}catch(NumberFormatException e){
				String msg = className+" 转换userId出错,userId:"+userId;
				LogUtil.writeLog(msg,Level.SEVERE);
			}
			blogService.saveBlog(blog, null);
			
			request.setAttribute("blog_content", blogContent);
			request.setAttribute("blog_title",title);
			response.setContentType("text/html;charset=utf-8");
			return "blog/showblog";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("info", "展示博客出错。");
		return "error";
	
	}
	
	@RequestMapping(value="/listblog")
	@ResponseBody
	public void listBlog(HttpServletRequest request,HttpServletResponse response,Model model){
		
		try {
			request.setCharacterEncoding("utf-8");
			int userId = (Integer) request.getSession().getAttribute("userId");
			List<Blog> blogList = blogService.findBlogByUserId(String.valueOf(userId));
			StringBuffer retStr = new StringBuffer();
			if(!CollectionUtils.isEmpty(blogList)){
				for(Blog blog:blogList){
					retStr.append(JSONObject.toJSONString(blog));
				}
			}
			Map<String,String> jsonMap = new HashMap<String, String>();
			jsonMap.put("totalSize", String.valueOf(blogList.size()));
			jsonMap.put("content", retStr.toString());
			String retJson = JSONObject.toJSONString(jsonMap);
			model.addAttribute("blogList",retJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
