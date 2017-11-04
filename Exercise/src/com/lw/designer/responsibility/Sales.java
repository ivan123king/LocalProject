package com.lw.designer.responsibility;

/**
 * 销售人员，批准折扣在5%以内的申请
 * @author lenovo
 *
 */
public class Sales extends PriceHandler{

	@Override
	public void processDiscount(float discount) {
		if(discount<0.05){
			System.out.format("%s批准了折扣：%.2f\n",this.getClass().getSimpleName(),discount);
		}else{
			successor.processDiscount(discount);
		}
	}

}
