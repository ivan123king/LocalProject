package com.lw.house.common.constants;

/**
 * 房屋用户关系
 * @author lenovo
 *
 */
public enum HouseUserType {

	SALE(1),BOOKMARK(2);
	
	public final Integer value;
	
	private HouseUserType(Integer value){
		this.value = value;
	}
}
