package com.lw.house.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lw.house.common.model.User;
import com.lw.house.common.page.PageParams;

@Mapper
public interface AgencyMapper {

	public List<User> selectAgent(@Param("user") User user,@Param("pageParams") PageParams pageParams);

	public Long selectAgentCount(@Param("user")User user);

}
