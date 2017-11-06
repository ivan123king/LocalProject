package com.lw.designer.proxy.dynamic_proxy;

import com.lw.designer.proxy.Moveable;

public class CarLogProxy implements Moveable{

private Moveable m = null;
	
	public CarLogProxy(Moveable m ) {
		this.m = m;
	}
	
	@Override
	public void move() {
		System.out.println("汽车日志开始");
		m.move();
		System.out.println("汽车日志结束");
	}

}
