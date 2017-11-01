package com.lw.designer.adaptation;

/**
 * 二项转三项插座适配器类
 * @author lenovo
 *
 */
public class TwoPlugAdapter implements ThreePlug {

	private GBTwoPlug plug;
	
	public TwoPlugAdapter(GBTwoPlug plug) {
		this.plug = plug;
	}
	
	@Override
	public void powerWithThree() {
		System.out.print("通过转换充电:");
		plug.powerWithTwo();
	}
}
