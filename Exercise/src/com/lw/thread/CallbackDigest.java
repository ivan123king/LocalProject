package com.lw.thread;

public class CallbackDigest implements Runnable {

	private int num1,num2;
	public CallbackDigest(int num1,int num2) {
		this.num1 = num1;
		this.num2 = num2;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			int num3 = this.num1+this.num2;
			CallbackDigestUserInterface.receiveData(num3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
