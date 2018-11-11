package com.lw.house.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.lw.house.common.model.City;

/**
 * 城市服务类
 * @author lenovo
 *
 */
@Service
public class CityService {

	public List<City> getAllCitys(){
		City city = new City();
		city.setCityCode("110000");
		city.setCityName("南京市");
		List<City> citys = Lists.newArrayList();
		return citys;
	}
}
