package com.lw.designer.strategy;

/**
 * 具体的鸭子类型
 * @author lenovo
 *
 */
public class GreenDuck extends Duck {

	public GreenDuck() {
		super();
		super.setFlyingStrategy(new FlyWithWin());//设置飞行方式
	}
	
	@Override
	public void display() {
		System.out.println("外观绿色");
	}

}
