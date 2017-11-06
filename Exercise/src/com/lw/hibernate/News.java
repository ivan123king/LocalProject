package com.lw.hibernate;

import java.util.Date;

public class News {
	private Integer id;
	private String title;
	private String author;
	private Date date;
	private String desc;
	
	
	
	public News() {}
	public News( String title, String author, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.date = date;
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", author=" + author
				+ ", date=" + date + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDesc(){
		return this.desc;
	}
	
	public void setDesc(String desc){
		this.desc = desc;
	}
}
