package com.lw.house.common.page;

/**
 * 分页帮助类
 * @author lenovo
 *
 */
public class PageParams {
	
	private static final Integer PAGE_SIZE = 2;
	
	private Integer pageSize;
	private Integer pageNo;
	private Integer offset;
	private Integer limit;
	
	public static PageParams build(Integer pageSize,Integer pageNo){
		if(pageSize==null) pageSize = PAGE_SIZE;
		if(pageNo==null) pageNo = 1;
		return new PageParams(pageSize, pageNo);
	}
	
	public PageParams(){
		this(PAGE_SIZE,1);
	}
	
	public PageParams(Integer pageSize,Integer pageNo){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.offset = pageSize*(pageNo-1);
		this.limit = pageSize;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	
}
