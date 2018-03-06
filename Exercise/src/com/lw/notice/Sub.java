package com.lw.notice;

public class Sub extends Super{

	private final String num ;
	
	Sub(){
		num = "now";
	}
	
	@Override
	public void over(){
		System.out.println(num);
	}
	
	public static void main(String[] args) {
		Sub s = new Sub();
		s.over();
	}
}
