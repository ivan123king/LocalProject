package com.lw.house.common.model;

import java.util.Date;

/**
 * 房屋用户关系实体类
 * @author lenovo
 *
 */
public class HouseUser {
	private Long id;
	private Long houseId;
	private Long userId;
	private Date  createTime;
	private Integer type;//表示收藏还是售卖
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHouseId() {
		return houseId;
	}
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

	
}
