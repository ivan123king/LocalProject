package com.lw.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.house.common.model.House;
import com.lw.house.service.RecommendService;

/**
 * 首页控制器
 * @author lenovo
 *
 */
@Controller
public class HomePageController {

	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("index")
	public String index(ModelMap modelMap){
		List<House> houses = recommendService.getLastest();
		modelMap.put("recomHouses", houses);
		return "homepage/index";
	}
	
	@RequestMapping("")
	public String home(ModelMap modelMap){
		return "redirect:/index";
	}
}
