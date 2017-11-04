package com.lw.designer.responsibility;

/**
 * 销售经理，可以批准10%的折扣率
 * @author lenovo
 *
 */
public class Manager extends PriceHandler {

	@Override
	public void processDiscount(float discount) {
		if(discount<0.1){
			System.out.format("%s批准了折扣：%.2f\n", this.getClass().getSimpleName(),discount);
		}else{
			successor.processDiscount(discount);
		}
		
	}

}
