package com.lw.blog.bean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

public class Blog {

	private int blogId;//博客ID
	private int userId;//用户ID
	private String title;//标题
	private String content;//内容
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date blogDate;//写作日期
	private String labels;//博客属性标签,","号分隔
	private int isDelete;//表示是否删除，0否，1是，使用Task定时清理
	private String userName;//作者
	
	@JSONField(name="blog_id")
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	
	@JSONField(name="user_id")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@JSONField(serialize=false)//blog对象转变为json时忽略content属性
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@JSONField(name="blog_date",format="YYYY-MM-dd")//HH:mm:ss
	public Date getBlogDate() {
		return blogDate;
	}
	public void setBlogDate(Date blogDate) {
		this.blogDate = blogDate;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	
	@JSONField(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@JSONField(serialize=false)
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	@JSONField(name="user_name")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
