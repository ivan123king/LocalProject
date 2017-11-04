package com.lw.designer.responsibility;

/**
 * CEO 可以批准50%的折扣请求，最后一个处理者
 * @author lenovo
 *
 */
public class CEO extends PriceHandler{

	@Override
	public void processDiscount(float discount) {
		if(discount<0.5){
			System.out.format("%s批准了折扣：%.2f\n",this.getClass().getSimpleName(),discount);
		}else{
			System.out.format("%s不批准折扣：%.2f\n",this.getClass().getSimpleName(),discount);
		}
		
	}

}
