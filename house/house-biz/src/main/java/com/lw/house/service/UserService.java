package com.lw.house.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.lw.house.common.model.User;
import com.lw.house.common.utils.BeanHelper;
import com.lw.house.common.utils.HashUtils;
import com.lw.house.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private MailService mailService;
	
	@Value("${file.prefix}")
	private String imgPrefix;
	
	public List<User> getUsers(){
		return userMapper.selectUsers();
	}
	
	/**
	 * 1.插入数据库，非激活状态，密码加盐同时md5加密,保存头像到本地
	 * 2. 生成key，绑定email
	 * 3. 发送邮件给用户
	 * @param account
	 * @return
	 */
	//增加事务注解，在发生异常时回滚
	/*
	 * 事务注解生效机制： 是需要在另一个类中调用addAccount方法才会生效，如果是本类中调用addAccount就不会生效
	 * 本质上是对UserService进行代理，所以只有外层调用代理对象才能实现事务，本类无法代理本类。
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean addAccount(User account){
		account.setPasswd(HashUtils.encryPassword(account.getPasswd()));
		List<String> imgList = fileService.getImgPath(Lists.newArrayList(account.getAvatarFile()));
		if(!imgList.isEmpty()){
			account.setAvatar(imgList.get(0));
		}
		BeanHelper.setDefaultProp(account, User.class);
		BeanHelper.onInsert(account);
		account.setEnable(0);
		userMapper.insert(account);
		//发送邮件通知
		mailService.registerNotify(account.getEmail());
		return true;
	}
	
	/**
	 * 激活用户账户
	 * @param key
	 * @return
	 */
	public boolean enable(String key){
		return mailService.enable(key);
	}
	
	/**
	 * 登录认证
	 * @param username
	 * @return
	 */
	public User auth(String username,String password){
		User user = new User();
		user.setEmail(username);
		user.setPasswd(HashUtils.encryPassword(password));
		List<User> list = getUserByQuery(user);
		if(!list.isEmpty()) return list.get(0);
		return null;
	}
	
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
	public List<User> getUserByQuery(User user){
		List<User> list = userMapper.selectUsersByQuery(user);
		list.forEach(u->{
			u.setAvatar(imgPrefix+u.getAvatar());
		});
		return list;
	}
	
	public void updateUser(User updateUser,String email){
		updateUser.setEmail(email);
		//此处利用反射对一些默认变量如时间做修改
		BeanHelper.onUpdate(updateUser);
		userMapper.update(updateUser);
	}
}
