package com.lw.designer.proxy;

/**
 * 聚合方式实现代理
 * @author lenovo
 *
 */
public class Car3 implements Moveable{
	//将car这实例放在类内部就称为聚合
	private Car car = null;
	public Car3(Car car) {
		this.car = car;
	}
	
	/**
	 * 使用car3调用move其实是car的move逻辑
	 * 同时可以在调用car的move逻辑前后添加自己的逻辑
	 */
	@Override
	public void move() {
		System.out.println("汽车开始行驶");
		car.move();
		System.out.println("汽车行驶结束");
	}

}
