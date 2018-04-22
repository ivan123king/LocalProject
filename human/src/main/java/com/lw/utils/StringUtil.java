package com.lw.utils;

public class StringUtil {

	public static boolean isNotEmpty(String... strs){
		if(strs!=null&&strs.length>0){
			for(String str:strs){
				if(str==null||"".equals(str)) return false;
			}
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String...strings ){
		if(strings!=null&&strings.length>0){
			for(String str:strings){
				if(str==null||"".equals(str)) return true;
			}
			return false;
		}
		return true;
	}
}
