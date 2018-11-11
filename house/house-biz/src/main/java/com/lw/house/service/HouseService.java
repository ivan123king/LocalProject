package com.lw.house.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.lw.house.common.constants.HouseUserType;
import com.lw.house.common.model.Community;
import com.lw.house.common.model.House;
import com.lw.house.common.model.HouseUser;
import com.lw.house.common.model.User;
import com.lw.house.common.model.UserMsg;
import com.lw.house.common.page.PageData;
import com.lw.house.common.page.PageParams;
import com.lw.house.common.utils.BeanHelper;
import com.lw.house.mapper.HouseMapper;

@Service
public class HouseService {

	// application.properties中配置的属性
	@Value("${file.prefix}")
	private String imgPrefix;

	@Autowired
	private HouseMapper houseMapper;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private FileService fileService;

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
	public List<House> queryAndSetImg(House query, PageParams pageParams) {
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
	
	/**
	 * 根据ID查询具体房屋
	 * @param houseId
	 * @return
	 */
	public House queryOneHouse(Long houseId){
		House query = new House();
		query.setId(houseId);
		List<House> houses = queryAndSetImg(query, PageParams.build(1,1));
		if(!houses.isEmpty()){
			return houses.get(0);
		}
		return null;
	}
	
	/**
	 * 为房屋添加评论或留言
	 * @param userMsg
	 */
	public void addUserMsg(UserMsg userMsg){
		BeanHelper.onInsert(userMsg);
		houseMapper.insertUserMsg(userMsg);
		User agent = agencyService.getAgentDetail(userMsg.getAgentId());
		//第三个参数是经纪人的邮箱，目的是让用户的留言发送邮件给经纪人，让其实时得知
		mailService.sendMail("来自用户"+userMsg.getEmail(), userMsg.getMsg(), agent.getEmail());
	}
	
	/**
	 * 查询房屋主人信息
	 * @param houseId
	 * @return
	 */
	public HouseUser getHouseUser(Long houseId){
		HouseUser houseUser =  houseMapper.selectSaleHouseUser(houseId);
		return houseUser;
	}
	
	/**
	 * 获取所有小区信息
	 * @return
	 */
	public List<Community> getAllCommunitys(){
		Community community = new Community();
		return houseMapper.selectCommunity(community);
	}
	
	/**
	 * 插入房产
	 * 1. 添加房产图片
	 * 2. 添加户型图片
	 * 3. 插入房产信息
	 * 4. 绑定用户到房产到关系
	 * @param house
	 * @param user
	 */
	public void addHouse(House house,User user){
		//1.设置房屋图片
		if(CollectionUtils.isNotEmpty(house.getHouseFiles())){
			//获取图片相对地址，同时使用","连接所有图片
			String images = Joiner.on(",").join(fileService.getImgPath(house.getHouseFiles()));
			house.setImages(images);
		}
		//设置户型图片
		if(CollectionUtils.isNotEmpty(house.getFloorPlanFiles())){
			String images = Joiner.on(",").join(fileService.getImgPath(house.getFloorPlanFiles()));
			house.setFloorPlan(images);
		}
		BeanHelper.onInsert(house);
		houseMapper.insert(house);
		
		//绑定用户到房产关系
		//第三个参数true表示收藏房屋，false表示添加房屋
		bindUser2House(house.getId(),user.getId(),false);
	}
	
	/**
	 * 绑定用户到房产关系
	 * @param houseId
	 * @param userId
	 * @param type
	 */
	public void bindUser2House(long houseId,long userId,boolean isCollect){
		int collectType = isCollect? HouseUserType.BOOKMARK.value:HouseUserType.SALE.value;
		HouseUser existHouseUser = houseMapper.selectHouseUser(houseId,userId,collectType);
		//已经存在房屋关系信息，不需要再操作
		if(existHouseUser!=null){
			return;
		}
		HouseUser houseUser = new HouseUser();
		houseUser.setHouseId(houseId);
		houseUser.setUserId(userId);
		houseUser.setType(collectType);
		//将没有设值的属性设置为"",如果是数据类型的，设置为0
		BeanHelper.setDefaultProp(houseUser, HouseUser.class);
		BeanHelper.onInsert(houseUser);
		houseMapper.insertHouseUser(houseUser);
	}

	/**
	 * 更新房屋评分
	 * @param id 房屋id
	 * @param rating 房屋评分
	 */
	public void updateRating(Long id, Double rating) {
		House house = queryOneHouse(id);
		Double oldRating = house.getRating();
		Double newRating = oldRating.equals(0)? rating:Math.min((oldRating+rating)/2,5);
		
//		House updateHouse = new House();
		
		house.setRating(newRating);
		BeanHelper.onUpdate(house);
		houseMapper.updateHouse(house);
	}

	/**
	 * 取消用户和房屋绑定
	 * @param houseId
	 * @param userId
	 * @param type 是 HouseUserType的枚举字段
	 */
	public void unBindUser2House(Long houseId, Long userId, HouseUserType type) {
		houseMapper.deleteHouseUser(houseId,userId,type.value);
	}
}
