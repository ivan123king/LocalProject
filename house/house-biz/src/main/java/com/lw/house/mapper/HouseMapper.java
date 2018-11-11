package com.lw.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lw.house.common.model.Community;
import com.lw.house.common.model.House;
import com.lw.house.common.model.HouseUser;
import com.lw.house.common.model.UserMsg;
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
	
	/**
	 * 添加留言或评论
	 * @param userMsg
	 * @return
	 */
	public int insertUserMsg(UserMsg userMsg);
	
	public HouseUser selectSaleHouseUser(@Param("id") Long houseId);
	
	public HouseUser selectHouseUser(@Param("houseId")Long houseId,@Param("userId")Long userId,@Param("type")Integer type);
	
	public void insertHouseUser(HouseUser houseUser);

	/**
	 * 更新房屋信息
	 * @param house
	 */
	public int updateHouse(House house);

	/**
	 * 删除用户房屋关系
	 * @param houseId
	 * @param userId
	 * @param type
	 */
	public void deleteHouseUser(@Param("houseId")Long houseId, @Param("userId")Long userId, @Param("type")Integer type);
}
