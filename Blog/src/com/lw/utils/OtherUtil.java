package com.lw.utils;

import java.util.List;
import java.util.UUID;

public class OtherUtil {

	public static boolean isNotEmpty(List list){
		if(list!=null&&list.size()>0)
			return true;
		return false;
	}
	
	public static String cUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		OtherUtil.cUUID();
	}
}

