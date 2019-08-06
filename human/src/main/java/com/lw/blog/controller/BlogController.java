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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.lw.blog.bean.Blog;
import com.lw.blog.service.BlogService;
import com.lw.utils.LogUtil;
import com.lw.utils.StringUtil;

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
			int userId = (Integer) request.getSession().getAttribute("userId");
			String title = request.getParameter("title");
			Blog blog = new Blog();
			blog.setContent(blogContent);
			blog.setBlogDate(new Date());
			blog.setTitle(title);
			try{
				blog.setUserId(userId);
			}catch(NumberFormatException e){
				String msg = className+" 转换userId出错,userId:"+userId;
				LogUtil.writeLog(msg,Level.SEVERE);
			}
			blogService.saveBlog(blog, null);
			Blog retBlog = blogService.findBlogByDate(userId, null);
			if(retBlog!=null){
				request.setAttribute("blogId", retBlog.getBlogId());
				request.setAttribute("blogContent", blogContent);
				request.setAttribute("blogTitle",title);
				response.setContentType("text/html;charset=utf-8");
				return "blog/showblog";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("info", "展示博客出错。");
		return "error";
	
	}
	
	@RequestMapping(value="/listblog")
	public String listBlog(HttpServletRequest request,HttpServletResponse response,Model model){
		String retJson = null;
		try {
			request.setCharacterEncoding("utf-8");
			int userId = (Integer) request.getSession().getAttribute("userId");
			List<Blog> blogList = blogService.findBlogByUserId(userId);
			Map<String,Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("totalSize", String.valueOf(blogList.size()));
			jsonMap.put("content",blogList);
			retJson = JSONObject.toJSONString(jsonMap);
			model.addAttribute("blogList",retJson);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "blog/bloglist";
	}
	
	@RequestMapping(value="/updateblog",method=RequestMethod.POST)
	public String updateBlog(HttpServletRequest request,HttpServletResponse response,Model model){
		String retJson = null;
		try {
			request.setCharacterEncoding("utf-8");
			int userId = (Integer) request.getSession().getAttribute("userId");
			String blogId = request.getParameter("blogId");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			Blog blog = new Blog();
			blog.setBlogId(Integer.parseInt(blogId));
			blog.setTitle(title);
			blog.setContent(content);
			blog.setUserId(userId);
			blogService.updateBlogById(blog, null);
			model.addAttribute("blogId",blogId);
			model.addAttribute("blogTitle", title);
			model.addAttribute("blogContent", content);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "error";
		}
		return "blog/showblog";
	}
	
	@RequestMapping(value="/deleteBlog/{blogId}")
	@ResponseBody
	public String deleteBlog(@PathVariable(value="blogId") int blogId,HttpServletRequest request){
		try{
			int userId = (Integer) request.getSession().getAttribute("userId");
//			blogService.deleteBlogByUserIdOrBlogId(blogId, userId, null);
		}
		catch(Exception e){
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
}
