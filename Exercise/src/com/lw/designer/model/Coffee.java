package com.lw.designer.model;

/**
 * 制备咖啡具体实现类
 * @author lenovo
 *
 */
public class Coffee extends RefreshBeverage {

	@Override
	protected void addCondiments() {
		System.out.println("加入糖和牛奶");
	}

	@Override
	protected void brew() {
		System.out.println("用沸水冲泡咖啡");
	}

}
