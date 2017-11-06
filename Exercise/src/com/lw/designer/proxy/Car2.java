package com.lw.designer.proxy;

/**
 * 继承代理：通过继承Car类来使用move逻辑，但内部真正的move逻辑其实是car的逻辑
 * car的代理类
 * @author lenovo
 *
 */
public class Car2 extends Car{

	@Override
	public void move() {
		System.out.println("汽车开始行驶");
		super.move();
		System.out.println("汽车行驶结束");
	}
}
