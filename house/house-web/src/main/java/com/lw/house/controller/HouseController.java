package com.lw.house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.house.common.model.House;
import com.lw.house.common.page.PageData;
import com.lw.house.common.page.PageParams;
import com.lw.house.service.HouseService;

@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;

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
		model.put("ps", ps);
		model.put("vo", query);
		return "house/listing";
	}
}
