package com.lw.house.common.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * 帮助信息加盐，防止明文出现
 * @author lenovo
 *
 */
public class HashUtils {

	private static final HashFunction FUNCTION = Hashing.md5();
	
	private static final String SALT = "itmac.club";
	
	/**
	 * 密码加盐，加密
	 * @param password
	 * @return
	 */
	public static String encryPassword(String password){
		HashCode hashCode = FUNCTION.hashString(password+SALT, Charsets.UTF_8);
		return hashCode.toString();
	}
}
