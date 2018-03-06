package com.lw.notice;

import java.util.Iterator;
import java.util.List;


/**
 * 泛型单例工厂
 * 
 * @author lenovo
 *
 */
public class FactoryG {

	interface UnaryFunction<T> {
		T apply(T arg);
	}

	private static UnaryFunction<Object> identity_function = new UnaryFunction<Object>() {
		public Object apply(Object arg) {
			return arg;
		}
	};
	
	public static <T> UnaryFunction<T> identityFunction(){
		return (UnaryFunction<T>) identity_function;
	}
	
	public static void main(String[] args) {
		String[] strs = {"a","b","c"};
		UnaryFunction<String> sameString = identityFunction();
		Number[] nums = {1,2,3};
		UnaryFunction<Number> sameNum = identityFunction();
		
		//UnaryFunction是一个泛型，identityFunction返回泛型化的接口。
		
	}
	
	/*
	 * 第二种： 继承泛型：
	 */
	interface Compareable<T>{
		int compareTo(T o);
	}
	
	public static <T extends Compareable<T>> T max(List<T> list){
		Iterator<T> i = list.iterator();
		T result = i.next();
		while(i.hasNext()){
			T t = i.next();
			if(t.compareTo(result)>0) result = t;
		}
		return result;
	}
}
