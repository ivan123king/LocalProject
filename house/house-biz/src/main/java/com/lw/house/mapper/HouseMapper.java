package com.lw.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lw.house.common.model.Community;
import com.lw.house.common.model.House;
import com.lw.house.common.page.PageParams;

@Mapper
public interface HouseMapper {

	/**
	 * 分页查询方法
	 * @param house
	 * @param pageParams
	 * @return
	 */
	public List<House> selectPageHouses(@Param("house")House house,@Param("pageParams")PageParams pageParams);
	
	/**
	 * 分页查询中得到总数的方法
	 * @param house
	 * @return
	 */
	public Long selectPageCount(@Param("house")House house);
	
	public int insert(House house);
	
	public List<Community> selectCommunity(Community community);
}
