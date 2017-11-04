package com.lw.designer.responsibility;

/**
 * 工厂类，设置销售折扣处理的层级关系
 * @author lenovo
 *
 */
public class PriceHandlerFactory {

	public static PriceHandler createPriceHandler(){
		
		PriceHandler sales = new Sales();
		PriceHandler manager = new Manager();
		PriceHandler ceo = new CEO();
		
		sales.setSuccessor(manager);
		manager.setSuccessor(ceo);
	
		return sales;
	}

}
