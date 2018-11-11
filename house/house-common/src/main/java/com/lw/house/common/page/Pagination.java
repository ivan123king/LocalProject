package com.lw.house.common.page;

import java.util.List;

import com.google.common.collect.Lists;


/**
 * 保存返回分页的页数，总数等
 * @author lenovo
 *
 */
public class Pagination {

	private int pageNo;//当前页数
	private int pageSize;//每页数据量
	private long totalCount;//总数
	//此集合是为了在页面展示时能展示   1 2 3 .... 12 13 这种样式的页数
	private List<Integer> pages = Lists.newArrayList();
	
	/**
	 * 
	 * @param pageSize
	 * @param pageNo
	 * @param totalCount  总数据量
	 */
	public Pagination(Integer pageSize,Integer pageNo,Long totalCount){
		this.totalCount = totalCount;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		for(int i=1;i<=pageNo;i++){
			pages.add(i);
		}
		//总共多少页
		Long pageCount = totalCount/pageSize+((totalCount%pageSize==0)?0:1);
		//当前请求页不是最后一页
		if(pageCount>pageNo){
			for(int i=pageNo+1;i<pageCount;i++){
				pages.add(i);
			}
		}
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public List<Integer> getPages() {
		return pages;
	}
	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}
	
	
}
