package com.lw.designer.strategy;

/**
 * 具体飞行方式： 用翅膀飞
 * @author lenovo
 *
 */
public class FlyWithWin implements FlyingStrategy {

	@Override
	public void fly() {
		System.out.println("用翅膀飞");
	}

}
