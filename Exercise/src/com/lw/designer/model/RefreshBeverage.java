package com.lw.designer.model;

/**
 * 抽象基类：为所有子类提供一个方法框架
 */
public abstract class RefreshBeverage {

	/**
	 * 制备饮料的模板方法，封装了所有子类共同遵循的步骤
	 * final不允许子类修改此步骤
	 */
	public final void perpareBeverageTemplate(){
		//1. 将水煮沸
		boilWater();
		//2. 泡制饮料
		brew();
		//3.倒入杯子
		pourInCup();
		//4. 加入调料
		addCondiments();
	}

	/**
	 * 加入调料
	 * 需要子类具体实现加入什么调料
	 */
	protected abstract void addCondiments();
	/**
	 * 倒入杯子
	 */
	private void pourInCup() {
		System.out.println("倒入杯子");
	}

	/**
	 * 泡制
	 * 需要子类定义如何泡制
	 */
	protected abstract void brew();

	/**
	 * 将水煮沸
	 */
	private void boilWater() {
		System.out.println("将水煮沸");
	}
}
