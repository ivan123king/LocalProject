package com.lw.house.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lw.house.common.model.Community;
import com.lw.house.common.model.House;
import com.lw.house.common.page.PageData;
import com.lw.house.common.page.PageParams;
import com.lw.house.mapper.HouseMapper;

@Service
public class HouseService {

	// application.properties中配置的属性
	@Value("${file.prefix}")
	private String imgPrefix;

	@Autowired
	private HouseMapper houseMapper;

	/**
	 * 1.查询小区 2. 添加图片服务器地址前缀 3. 构建分页结果
	 * 
	 * @param query
	 * @param pageParams
	 */
	public PageData<House> queryHouse(House query, PageParams pageParams) {
		List<House> houses = Lists.newArrayList();
		// 查询小区
		if (!Strings.isNullOrEmpty(query.getName())) {
			Community community = new Community();
			community.setName(query.getName());
			List<Community> communities = houseMapper
					.selectCommunity(community);
			if (!communities.isEmpty()) {
				query.setCommunityId(communities.get(0).getId());
			}
		}
		// 添加图片服务器地址前缀
		houses = queryAndSetImg(query, pageParams);
		//构造分页结果
		Long count = houseMapper.selectPageCount(query);
		return PageData.buildPage(houses, count, pageParams.getPageSize(),
				pageParams.getPageNo());
	}

	/**
	 * 查询数据库，获取房屋信息
	 * 设置图片地址
	 * @param query
	 * @param pageParams
	 * @return
	 */
	private List<House> queryAndSetImg(House query, PageParams pageParams) {
		List<House> houses = houseMapper.selectPageHouses(query, pageParams);
		houses.forEach((h) -> {
			h.setFirstImg(imgPrefix + h.getFirstImg());
			h.setImageList(h.getImageList().stream()
					.map(img -> imgPrefix + img).collect(Collectors.toList()));
			h.setFloorPlanList(h.getFloorPlanList().stream()
					.map(img -> imgPrefix + img).collect(Collectors.toList()));
		});
		return houses;
	}
}
