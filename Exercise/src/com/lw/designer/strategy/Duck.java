package com.lw.designer.strategy;

public abstract class Duck {
	
	//使用组合方式实现飞行行为，比实现接口好
	private FlyingStrategy flyingStrategy = new FlyNoWay();
	
	public void setFlyingStrategy(FlyingStrategy flyingStrategy) {
		this.flyingStrategy = flyingStrategy;
	}
	
	public void fly(){
		flyingStrategy.fly();
	}
	
	/**
	 * 鸭叫
	 * 通用行为
	 */
	public void quack(){
		System.out.println("嘎嘎嘎");
	}
	
	/**
	 * 显示鸭子
	 */
	public abstract void display();

}
