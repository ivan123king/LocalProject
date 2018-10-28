package com.lw.house.common.page;

import java.util.List;

/**
 * 返回分页结果
 * @author lenovo
 *
 */
public class PageData<T> {

	private List<T> list;
	
	private Pagination pagination;
	
	public PageData(Pagination pagination,List<T> list){
		this.pagination = pagination;
		this.list = list;
	}

	/**
	 * 
	 * @param list
	 * @param count 总数据量，不是List的大小
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public static <T> PageData<T> buildPage(List<T> list,long count,Integer pageSize,Integer pageNo){
		Pagination pagination = new Pagination(pageSize, pageNo, count);
		return new PageData<>(pagination,list);
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}

