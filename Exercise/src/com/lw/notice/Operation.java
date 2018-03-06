package com.lw.notice;

/**
 * 枚举中定义抽象方法并实现
 * @author lenovo
 *
 */
public enum Operation {

	PLUS{double apply(double x,double y){return x+y;}},
	MINUS{double apply(double x,double y){return x-y;}},
	TIMES{double apply(double x,double y){return x*y;}},
	DIVIDE{
		@Override
		double apply(double x, double y) {
			return x/y;
		}};
	
	abstract double apply(double x,double y);
}
