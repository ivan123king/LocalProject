package com.lw.designer.proxy;

public class Car implements Moveable{

	@Override
	public void move() {
		System.out.println("汽车行驶中.");
	}

}
