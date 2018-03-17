package com.lw.designer.adaptation;

public class NoteBook {

	private ThreePlug plug;
	
	public NoteBook(ThreePlug plug) {
		this.plug = plug;
	}
	
	/**
	 * 使用插座充电
	 */
	public void change(){
		plug.powerWithThree();
	}
	
	
	public static void main(String[] args) {
//		GBTwoPlug two = new GBTwoPlug();
//		ThreePlug plug = new TwoPlugAdapter(two);
		ThreePlug plug = new TwoPlugAdapterExtends();
		NoteBook nb = new NoteBook(plug);
		nb.change();
	}
}
