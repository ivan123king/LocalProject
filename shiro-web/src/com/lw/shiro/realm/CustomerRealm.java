package com.lw.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

/**
 * 自定义Realm 
 * 认证Realm
 * @author lenovo
 *
 */
public class CustomerRealm extends AuthenticatingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token)
			throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken)token;
		String userName = upToken.getUsername();
		char[] password = upToken.getPassword();
		Object principal = userName;
		Object credentials = "数据库中保存的密码";
		String realmName = getName();
		/*
		 * 登录流程【shiro_easy_01 Quickstart.java】中 currentUser.login(token);
		 * 此方法中会获取所有Realm.
		 * 此处是一个用来验证用户名和密码的。用户从表单传递过来的username，password必须和此处的principal和credentials匹配才算登录成功。
		 * 
		 * 个人看法：用户名和密码的验证可以放在Controller中验证完毕
		 * 或者说此处调用Service得到数据库中的用户名和密码，然后赋值给principal,credentials来验证。
		 * 毕竟是一个用户管理框架提供登录验证是情有可原。
		 */
		AuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
		return info;
	}
	
	public static void main(String[] args) {
		SimpleHash h01 =  new SimpleHash("MD5", "123456", null, 1024);
		SimpleHash h02 =  new SimpleHash("MD5", "123456", null, 1);
		System.out.println(h01+"\r\n"+h02);
	}

}
