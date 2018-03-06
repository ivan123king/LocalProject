package com.lw.thread;

public class InstanceCallbackDigest  implements Runnable{

	private InstanceCallbackDigestUserInterface callee;
	private int num1,num2;
	
	public InstanceCallbackDigest(int num1,int num2,InstanceCallbackDigestUserInterface callee) {
		this.num1 = num1;
		this.num2 = num2;
		this.callee = callee;
	}
	
	@Override
	public void run() {
		try{
			Thread.sleep(1000);
			int num3 = num1+num2;
			callee.receiveData(""+num3+"="+num1+"+"+num2);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

}
