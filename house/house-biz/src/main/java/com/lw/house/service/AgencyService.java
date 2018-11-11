package com.lw.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lw.house.common.model.User;
import com.lw.house.common.page.PageData;
import com.lw.house.common.page.PageParams;
import com.lw.house.mapper.AgencyMapper;

/**
 * 经纪人服务类
 * @author lenovo
 *
 */
@Service
public class AgencyService {

	@Autowired
	private AgencyMapper agencyMapper;
	
	@Value("${file.prefix}")
	private String imgPrefix;
	
	/**
	 * 获取经纪人信息
	 * 添加用户头像
	 * @param userId
	 * @return
	 */
	public User getAgentDetail(Long userId){
		User user = new User();
		user.setId(userId);
		user.setType(2);//经纪人类型
		List<User> users = agencyMapper.selectAgent(user, PageParams.build(1, 1));
		if(users.isEmpty()) return null;
		setImg(users);//设置经济人头像
		return users.get(0);
	}
	
	private void setImg(List<User> users){
		users.forEach((user)->{
			user.setAvatar(imgPrefix+user.getAvatar());
		});
	}
	
	/**
	 * 查询经纪人列表
	 * @param pageParams
	 * @return
	 */
	public PageData<User> getAllAgent(PageParams pageParams){
		List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
		setImg(agents);
		Long count = agencyMapper.selectAgentCount(new User());
		return PageData.buildPage(agents,count,pageParams.getPageSize(),pageParams.getPageNo());
	}
}
