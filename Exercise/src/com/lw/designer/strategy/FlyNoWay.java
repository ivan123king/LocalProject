package com.lw.designer.strategy;

/**
 * 具体飞行方式： 不会飞行
 * @author lenovo
 *
 */
public class FlyNoWay implements FlyingStrategy {

	@Override
	public void fly() {
		System.out.println("不会飞行");
	}

}
