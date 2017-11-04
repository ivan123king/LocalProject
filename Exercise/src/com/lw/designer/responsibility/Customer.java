package com.lw.designer.responsibility;

/**
 * 客户：申请折扣
 * @author lenovo
 *
 */
public class Customer {
	
	private PriceHandler priceHandler;
	
	public void setPriceHandler(PriceHandler priceHandler) {
		this.priceHandler = priceHandler;
	}
	
	public void requestDiscount(float discount){
		priceHandler.processDiscount(discount);
	}
	
	public static void main(String[] args){
		Customer c = new Customer();
		c.setPriceHandler(PriceHandlerFactory.createPriceHandler());
		c.requestDiscount(0.08f);
	}
}
