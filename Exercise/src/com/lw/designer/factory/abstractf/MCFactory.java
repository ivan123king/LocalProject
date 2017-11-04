package com.lw.designer.factory.abstractf;

/**
 * 圣诞系列工厂，提供圣诞系列的男女
 * @author lenovo
 *
 */
public class MCFactory implements PersonFactory{

	@Override
	public Boy getBoy() {
		return new MCBoy();
	}

	@Override
	public Girl getGirl() {
		return new MCGirl();
	}

}
