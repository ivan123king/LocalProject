package com.lw.designer.proxy.cglib_proxy;

public class Client {

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		Train t = (Train) proxy.getProxy(Train.class);
		t.move();
	}
}
