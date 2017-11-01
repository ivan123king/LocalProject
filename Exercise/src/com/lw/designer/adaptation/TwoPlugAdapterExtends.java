package com.lw.designer.adaptation;

public class TwoPlugAdapterExtends extends GBTwoPlug implements ThreePlug{

	@Override
	public void powerWithThree() {
		System.out.print("借助继承适配器：");
		this.powerWithTwo();
	} 
}
