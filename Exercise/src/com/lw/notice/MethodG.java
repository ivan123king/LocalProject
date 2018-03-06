package com.lw.notice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 方法泛型化
 * @author lenovo
 *
 */
public class MethodG {

	public static <E> Set<E> union(Set<E> s1,Set<E> s2){
		Set<E> result = new HashSet<E>();
		result.addAll(s2);
		return result;
	}
	
	public static <K,V> HashMap<K,V> newHashMap(){
		return new HashMap<K,V>();
		
		/*
		 * 实例：
		 * Map<String,List<String>> agrams = newHashMap();
		 */
	}
	
	public static void main(String[] args) {
		Set<String> alph = new HashSet<String>(Arrays.asList("a","b","c"));
		Set<String> nums = new HashSet<String>(Arrays.asList("1","2","3"));
		Set<String> alnum = union(alph,nums);
		System.out.println(alnum);
		
	}
}
