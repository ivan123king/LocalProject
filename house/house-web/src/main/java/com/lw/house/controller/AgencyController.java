package com.lw.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.house.common.model.House;
import com.lw.house.common.model.User;
import com.lw.house.common.page.PageData;
import com.lw.house.common.page.PageParams;
import com.lw.house.service.AgencyService;
import com.lw.house.service.HouseService;

/**
 * 经纪人接口
 * @author lenovo
 *
 */
@Controller
public class AgencyController {
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private HouseService houseService;
	
	/**
	 * 查询经济人列表
	 * @param pageSize
	 * @param pageNum
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/agency/agentList")
	public String agentList(Integer pageSize,Integer pageNum,ModelMap modelMap){
		
		PageData<User> pageData = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
		modelMap.put("ps", pageData);
		return "/agent/agentList";
	}
	
	/**
	 * 查询经纪人详情接口
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/agency/agentDetail")
	public String agentDetail(Long id,ModelMap modelMap){
		User user = agencyService.getAgentDetail(id);
		House query = new House();
		query.setUserId(id);
		query.setBookmarked(false);
		PageData<House> pageData = houseService.queryHouse(query, new PageParams(3,1));
		if(pageData!=null){
			modelMap.put("bindHouses", pageData);
		}
		modelMap.put("agent", user);
		return "/agent/agentDetail";
	}
}
