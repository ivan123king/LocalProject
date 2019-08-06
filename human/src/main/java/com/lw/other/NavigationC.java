package com.lw.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.blog.bean.Blog;
import com.lw.blog.dao.BlogDao;
import com.lw.blog.service.BlogService;

/**
 * 导航到页面的Controller类
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value="/navigation")
public class NavigationC {
	
	@Autowired
	public BlogService blogService;

	/**
	 * 前往注册页面
	 * @return
	 */
	@RequestMapping(value="/register")
	public String toRegister(){
		return "users/register";
	}
	
	/**
	 * 前往登录页面
	 * @return
	 */
	@RequestMapping(value="/login")
	public String toLogin(){
		return "users/login";
	}
	
	/**
	 * 前往写博客页面
	 * @return
	 */
	@RequestMapping(value="/writeblog")
	public String towriteBlog(){
		return "blog/writeblog";
	}
	
	@RequestMapping(value="/bloglist")
	public String toBlogList(){
		return "redirect:/blog/listblog";
	}
	
	@RequestMapping(value="/updateblog")
	public String toUpdateBlog(int blogId,Model model){
		Blog blog = blogService.findBlogById(blogId);
		if(blog==null){
			return "error";
		}
		model.addAttribute("blogId",blog.getBlogId());
		model.addAttribute("blogTitle",blog.getTitle());
		model.addAttribute("blogContent",blog.getContent());
		return "blog/updateblog";
	}
	
}
