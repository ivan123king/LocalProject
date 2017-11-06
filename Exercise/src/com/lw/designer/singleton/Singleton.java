package com.lw.designer.singleton;

/**
 * 单例：返回唯一的实例
 * @author lenovo
 *
 */
public class Singleton {

	/**
	 * 如果这里直接在声明single对象时就new对象，就是饿汉模式
	 * 因为在Singleton加载时就已经new了这个静态变量出来了
	 */
	private static Singleton single = null;
	
	private Singleton(){
		
	}
	
	/**
	 * 懒汉模式单例，需要时才new对象出来
	 * @return
	 */
	public static Singleton getInstance(){
		if(single==null) single = new Singleton();
		return single;
	}
}
