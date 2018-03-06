package com.lw.notice;

/**
 * 枚举中定义抽象方法并实现
 * @author lenovo
 *
 */
public enum OperationTwo {

	PLUS("+"){double apply(double x,double y){return x+y;}},
	MINUS("-"){double apply(double x,double y){return x-y;}},
	TIMES("*"){double apply(double x,double y){return x*y;}},
	DIVIDE("/"){
		@Override
		double apply(double x, double y) {
			return x/y;
		}},
	REMAINDER("%"){double apply(double x,double y){return x%y;}};//()带括号这种需要构造方法
	
	abstract double apply(double x,double y);
	
	private final String symbol;
	/*
	 * 构造方法里的symbol是由PLUS("+") 这个括号中传递过来的String
	 */
	OperationTwo(String symbol){ this.symbol = symbol;}
	
	@Override
	public String toString(){return symbol;}
	
	public static void main(String[] args) {
		double x = 3,y=4;
		for(OperationTwo ot:OperationTwo.values()){
			System.out.printf("%f %s %f = %f%n", x,ot,y,ot.apply(x, y));
			/*
			 * 输出：
			 * 3.000000 + 4.000000 = 7.000000
				3.000000 - 4.000000 = -1.000000
				3.000000 * 4.000000 = 12.000000
				3.000000 / 4.000000 = 0.750000
				3.000000 % 4.000000 = 3.000000
			 */
		}
	}
}
