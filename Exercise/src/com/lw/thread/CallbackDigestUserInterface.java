package com.lw.thread;

public class CallbackDigestUserInterface {

	public static void receiveData(int total){
		System.out.println(total);
	}
	
	public static void main(String[] args) {
		CallbackDigest call = new CallbackDigest(3, 4);
		new Thread(call).start();
	}
}
