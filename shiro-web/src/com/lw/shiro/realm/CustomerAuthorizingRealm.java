package com.lw.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义授权Realm
 * @author lenovo
 *
 */
public class CustomerAuthorizingRealm extends AuthorizingRealm{

	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//1.获取此登录用户信息,其实principal就是username(看CustomerRealm类中设置的就明白了)
		Object principal = arg0.getPrimaryPrincipal();
		//2.获取此用户对应的角色，可以查询数据库得到此用户的角色，此处就人工临时设置
		Set<String> roles = new HashSet<String>();
		roles.add("admin");//默认拥有admin角色
		if("user".equals(principal)){
			roles.add("user");//设置为user角色
		}
		//3.返回此用户拥有的角色
		AuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		//备注：返回对应角色后，框架就会自动校验访问某个URL是否就是此角色，从而完成授权
		return info;
	}

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

}
