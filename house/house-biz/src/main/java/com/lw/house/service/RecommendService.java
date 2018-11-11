package com.lw.house.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.lw.house.common.model.House;
import com.lw.house.common.page.PageParams;

/**
 * 热门房产
 * @author lenovo
 *
 */
@Service
public class RecommendService {

	private static final String HOT_HOUSE_KEY = "hot_house";
	
	@Autowired
	private HouseService houseService;
	
	public void increase(Long id){
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.zincrby(HOT_HOUSE_KEY,1.0D, ""+id);
		//删除列表排名10以后的数据
		jedis.zremrangeByRank(HOT_HOUSE_KEY, 10, -1);
		jedis.close();
	}
	
	/**
	 * 获取热门房产信息
	 * @return
	 */
	public List<Long> getHot(){
		Jedis jedis = new Jedis("127.0.0.1",6379);
		//按照分数从高到低获取
		Set<String> idSet = jedis.zrevrange(HOT_HOUSE_KEY, 0, -1);
		jedis.close();
		List<Long> ids = idSet.stream().map(Long::parseLong).collect(Collectors.toList());
		return ids;
	}
	
	public List<House> getHouse(Integer size){
		House query = new House();
		List<Long> list = getHot();
		list = list.subList(0,Math.min(list.size(), size));
		if(list.isEmpty()) return Lists.newArrayList();
		query.setIds(list);
		List<House> houses = houseService.queryAndSetImg(query, PageParams.build(size, 1));
		final List<Long> orderList = list;
		
//		list.sort(new Comparator<Long>() {
//			@Override
//			public int compare(Long o1, Long o2) {
//				Long  o = o1-o2;//从大到小排序，如果o2小，那么o2排后面
//				return o.intValue();
//			}
//		});
		Ordering<House> houseSort = Ordering.natural().onResultOf(hs->{
			//根据id从0开始排序
			return orderList.indexOf(hs.getId());
		});
		return houseSort.sortedCopy(houses);
	}
	
	/**
	 * 获取最近添加的房屋信息
	 * @return
	 */
	public List<House> getLastest(){
		House query = new House();
		query.setSort("create_time");
		List<House> houses = houseService.queryAndSetImg(query, PageParams.build(8,1));
		return houses;
	}
}
