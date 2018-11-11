package com.lw.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lw.house.common.constants.CommonConstants;
import com.lw.house.common.constants.HouseUserType;
import com.lw.house.common.model.House;
import com.lw.house.common.model.HouseUser;
import com.lw.house.common.model.User;
import com.lw.house.common.model.UserMsg;
import com.lw.house.common.page.PageData;
import com.lw.house.common.page.PageParams;
import com.lw.house.common.result.ResultMsg;
import com.lw.house.service.AgencyService;
import com.lw.house.service.CityService;
import com.lw.house.service.HouseService;
import com.lw.house.service.RecommendService;
import com.lw.house.web.interceptor.UserContext;

@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RecommendService recommendService;

	/**
	 * 房产列表接口
	 * 1. 分页
	 * 2. 小区搜索，类型搜索
	 * 3. 排序
	 * 4. 支持展示图片，价格，标题，地址等信息
	 * @return
	 */
	@RequestMapping(value="/house/list")
	public String houseList(Integer pageSize,Integer pageNo,House query,ModelMap model){
		PageData<House> ps = houseService.queryHouse(query,PageParams.build(pageSize, pageNo));
		List<House> hotHouses = recommendService.getHouse(CommonConstants.RECOM_SIZE);//热门房产
		model.put("recomHouses", hotHouses);
		model.put("ps", ps);
		model.put("vo", query);
		return "house/listing";
	}
	
	/**
	 * 查询房屋详情
	 * 查询关联经纪人
	 * @param id
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houdeDetail(Long id,ModelMap modelMap){
		//此处id可能为null,house会为空
		House house = houseService.queryOneHouse(id);
		recommendService.increase(id);//点击详情给此房屋热门增加一
		HouseUser houseUser = houseService.getHouseUser(id);
		//houseUser没判空，容易空指针异常
		if(houseUser.getUserId()!=null && houseUser.getUserId().equals(0)){
			modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
		}
		List<House> hotHouses = recommendService.getHouse(CommonConstants.RECOM_SIZE);//热门房产
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("house", house);
		return "/house/detail";
	}
	
	/**
	 * 留言评论等
	 * @param userMsg
	 * @return
	 */
	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg){
		houseService.addUserMsg(userMsg);
		return "redirect:/house/detail?id="+userMsg.getHouseId();
	}
	
	/**
	 * 跳转到添加房屋信息页面接口
	 * @param map
	 * @return
	 */
	@RequestMapping("house/toAdd")
	public String toAdd(ModelMap map){
		map.put("citys",cityService.getAllCitys());
		map.put("communitys", houseService.getAllCommunitys());
		return "house/add";
	}
	
	
	/**
	 * 添加房屋信息接口
	 * 1. 获取用户
	 * 2. 设置房屋状态
	 * 3. 添加房产
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="house/add")
	public String doAdd(House house,ModelMap modelMap){
		User user = UserContext.getUser();
		//设置房屋状态为上架
		house.setState(CommonConstants.HOUSE_STATE_UP);
		houseService.addHouse(house,user);
		return "redirect:/house/ownlist";
	}
	
	/**
	 * 个人房产列表接口
	 */
	@RequestMapping("house/ownlist")
	public String ownList(House house,Integer pageNum,Integer pageSize,ModelMap modelMap){
		User user = UserContext.getUser();
		house.setUserId(user.getId());
		house.setBookmarked(false);//false表示房屋是售卖类型
		
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "own");
		
		return "house/ownlist";
	}
	
	/**
	 * 房屋评分接口
	 * @param rating  房屋评分
	 * @param id 房屋id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("house/rating")
	public ResultMsg houseRate(Double rating,Long id){
		houseService.updateRating(id,rating);
		return ResultMsg.successMsg("ok");
	}
	
	/**
	 * 房屋收藏接口
	 * @param id 房屋ID
	 */
	@ResponseBody
	@RequestMapping("house/bookmark")
	public ResultMsg bookmark(Long id){
		User user = UserContext.getUser();
		houseService.bindUser2House(id, user.getId(), true);
		return ResultMsg.successMsg("ok");
	}
	
	/**
	 * 取消收藏
	 * @param id 房屋ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("house/unbookmark")
	public ResultMsg unBookmark(Long id){
		User user = UserContext.getUser();
		houseService.unBindUser2House(id, user.getId(), HouseUserType.BOOKMARK);
		return ResultMsg.successMsg("ok");
	}
	
	/**
	 * 删除房屋售卖信息
	 * @param id 房屋ID
	 * @param pageType
	 * @return
	 */
	@RequestMapping("house/del")
	public String delsale(Long id,String pageType){
		User user = UserContext.getUser();
		houseService.unBindUser2House(id, user.getId(),pageType.equals("own")? HouseUserType.SALE:HouseUserType.BOOKMARK);
		return "redirect:/house/ownlist";
	}
	
	/**
	 * 收藏列表接口
	 * @param house
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("house/bookmarked")
	public String bookmarked(House house,Integer pageNum,Integer pageSize,ModelMap modelMap){
		User user = UserContext.getUser();
		house.setBookmarked(true);
		house.setUserId(user.getId());
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "book");
		return "/house/ownlist";
	}
	
}
