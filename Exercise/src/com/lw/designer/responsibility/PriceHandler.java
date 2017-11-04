package com.lw.designer.responsibility;

/**
 * 价格处理人，负责处理客户折扣申请
 * @author lenovo
 *
 */
public abstract class PriceHandler {

	protected PriceHandler successor;
	
	public void setSuccessor(PriceHandler successor) {
		this.successor = successor;
	}
	/**
	 * 处理折扣申请
	 * @param discount
	 */
	public abstract void processDiscount(float discount);
}
