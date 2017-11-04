package com.lw.designer.model;

/**
 * 制备茶的具体实现类
 * @author lenovo
 *
 */
public class Tea extends RefreshBeverage {

	@Override
	protected void addCondiments() {
		System.out.println("加入柠檬");
	}

	@Override
	protected void brew() {
		System.out.println("用沸水浸泡茶叶");
	}

}
