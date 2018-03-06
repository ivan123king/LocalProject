package com.lw.thread;

public class InstanceCallbackDigestUserInterface {

	public void receiveData(String str){
		System.out.println(str);
	}
	
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			InstanceCallbackDigestUserInterface d = new InstanceCallbackDigestUserInterface();
			InstanceCallbackDigest callback = new InstanceCallbackDigest(i,i+1,d);
			new Thread(callback).start();
		}
	}
}
