package com.lw.house.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.lw.house.common.model.User;
import com.lw.house.mapper.UserMapper;

@Service
public class MailService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Value("${domain.name}")
	private String domainName;
	
	/**
	 * 缓存对象，最大缓存100个值，每个值设置15分钟的失效时间，失效使用RemovalListener对象监听
	 */
	private final Cache<String, String> registerCache = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).removalListener(new RemovalListener<String, String>() {

		@Override
		public void onRemoval(RemovalNotification<String, String> notification) {
			//缓存失效，根据用户email删除此用户，此代表注册了未激活的用户
			userMapper.delete(notification.getValue());
		}
	}).build();

	/**
	 * 发送邮件
	 * @param title
	 * @param url
	 * @param email
	 */
	public void sendMail(String title,String url,String email){
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(email);
		message.setText(url);
		mailSender.send(message);
	}
	
	/**
	 * 发送邮件
	 * 1. 缓存key-email关系
	 * 2. 使用spring mail 发送邮件
	 * 3. 异步发送
	 * @param email
	 */
	//@Async将此任务放入线程池。使用异步方式操作,同样生效不能是本类内部调用
	@Async
	public void registerNotify(String email){
		//随机生成十位长度的字符串
		String randomKey = RandomStringUtils.randomAlphabetic(10);
		registerCache.put(randomKey, email);
		String url = "http://"+domainName+"/accounts/verify?key="+randomKey;
		sendMail("激活链接",url,email);
	}
	
	/**
	 * 激活启用账号
	 * @param key
	 * @return
	 */
	public boolean enable(String key){
		String email = registerCache.getIfPresent(key);
		if(StringUtils.isBlank(email)) return false;
		User updateUser = new User();
		updateUser.setEmail(email);
		updateUser.setEnable(1);
		userMapper.update(updateUser);
		registerCache.invalidate(key);
		return true;
	}
}
