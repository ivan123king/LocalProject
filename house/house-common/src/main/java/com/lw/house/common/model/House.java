package com.lw.house.common.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 房屋展示对象
 * @author lenovo
 *
 */
public class House {

	private Long id;
	private Integer type;//房屋类型
	private Integer price;
	private String name;
	private String images;//房屋图片地址，多个地址逗号分隔
	private Integer area;//面积
	private Integer beds;//卧室数量
	private Integer baths;//卫生间数量
	private Double rating;//评分
	private String remarks;//描述
	private String properties;//其他属性
	private String floorPlan;//户型图地址，多个地址逗号分隔
	private String tags;//标签
	private Date createTime;//创建时间
	private Integer cityId;//所属城市ID
	private Integer communityId;//所属社区id 
	private String address;//所在地址
	private String firstImg;//第一张图片
	
	private List<String> imageList = Lists.newArrayList();
	private List<String> floorPlanList = Lists.newArrayList();
	
	private List<MultipartFile> houseFiles;//房屋文件列表
	private List<MultipartFile> floorPlanFiles;//房屋户型图文件列表
	
	private Long userId;
	private boolean bookmarked;
	private Integer state;
	private List<Long> ids;
	
	private String sort = "time_desc";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
		if(!Strings.isNullOrEmpty(images)){
			List<String> list = Splitter.on(",").splitToList(images);
			if(!list.isEmpty()){
				this.firstImg = list.get(0);
				this.imageList = list;
			}
		}
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getBeds() {
		return beds;
	}
	public void setBeds(Integer beds) {
		this.beds = beds;
	}
	public Integer getBaths() {
		return baths;
	}
	public void setBaths(Integer baths) {
		this.baths = baths;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProperties() {
		return properties;
	}
	public void setProperties(String properties) {
		this.properties = properties;
	}
	public String getFloorPlan() {
		return floorPlan;
	}
	public void setFloorPlan(String floorPlan) {
		this.floorPlan = floorPlan;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFirstImg() {
		return firstImg;
	}
	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public List<String> getFloorPlanList() {
		return floorPlanList;
	}
	public void setFloorPlanList(List<String> floorPlanList) {
		this.floorPlanList = floorPlanList;
	}
	public List<MultipartFile> getHouseFiles() {
		return houseFiles;
	}
	public void setHouseFiles(List<MultipartFile> houseFiles) {
		this.houseFiles = houseFiles;
	}
	public List<MultipartFile> getFloorPlanFiles() {
		return floorPlanFiles;
	}
	public void setFloorPlanFiles(List<MultipartFile> floorPlanFiles) {
		this.floorPlanFiles = floorPlanFiles;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public boolean isBookmarked() {
		return bookmarked;
	}
	public void setBookmarked(boolean bookmarked) {
		this.bookmarked = bookmarked;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
	
}
