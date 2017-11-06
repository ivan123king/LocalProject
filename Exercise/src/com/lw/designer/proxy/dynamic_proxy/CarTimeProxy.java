package com.lw.designer.proxy.dynamic_proxy;

import com.lw.designer.proxy.Moveable;

public class CarTimeProxy implements Moveable {

	private Moveable m = null;
	
	public CarTimeProxy(Moveable m ) {
		this.m = m;
	}
	
	@Override
	public void move() {
		System.out.println("汽车时间开始");
		m.move();
		System.out.println("汽车时间结束");
	}

}
