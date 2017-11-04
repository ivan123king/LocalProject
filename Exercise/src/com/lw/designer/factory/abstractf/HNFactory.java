package com.lw.designer.factory.abstractf;

/**
 * 新年系列工厂：提供新年系列男女
 * @author lenovo
 *
 */
public class HNFactory implements PersonFactory {

	@Override
	public Boy getBoy() {
		return new HNBoy();
	}

	@Override
	public Girl getGirl() {
		return new HNGirl();
	}

}
