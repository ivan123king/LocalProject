package com.lw.house.controller;

import org.apache.commons.lang3.StringUtils;

import com.lw.house.common.model.User;
import com.lw.house.common.result.ResultMsg;

public class UserHelper {

	public static ResultMsg validate(User account){
		if(StringUtils.isBlank(account.getEmail())){
			return ResultMsg.errorMsg("Email有误");
		}
		if(StringUtils.isBlank(account.getName())){
			return ResultMsg.errorMsg("Name有误");
		}
		if(StringUtils.isAnyBlank(account.getPasswd(),account.getConfirmPasswd())
				|| !account.getPasswd().equals(account.getConfirmPasswd())){
			return ResultMsg.errorMsg("密码或确认密码有误");
		}
//		if(account.getPasswd().length()<6){
//			return ResultMsg.errorMsg("密码大于6位。");
//		}
		return ResultMsg.successMsg(null);
	}
}
