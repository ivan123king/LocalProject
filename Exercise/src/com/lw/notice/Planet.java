package com.lw.notice;

/**
 * 枚举类型
 * 在枚举类型中可以定义方法，定义枚举值等。
 * @author lenovo
 *
 */
public enum Planet {
	MERCURY(3.302e+23,2.439e6),//如此设计需要构造函数  Planet(double mass,double radius)
	VENUS(4.869e+24,6.052e6),
	EARTH(5.975e+24,6.378e6),
	MARS(6.419e+23,3.393e6);
	
	private final double mass;
	private final double radius;
	private final double surfaceGravity;
	
	private static final double G = 6.67300E-11;
	
	Planet(double mass,double radius){
		this.mass = mass;
		this.radius = radius;
		surfaceGravity = G*mass/(radius*radius);//计算表面重力
	}
	
	public double mass(){return mass;}
	public double radius(){return radius;}
	public double surfaceGravity(){return surfaceGravity;}
	
	/**
	 * 计算行星表面重量
	 * @param mass
	 * @return
	 */
	public double surfaceWeight(double mass){
		return mass*surfaceGravity;
	}
	
	public static void main(String[] args) {
		double earthWeight = 20e6;
		double mass = earthWeight/Planet.EARTH.surfaceGravity();
		for(Planet p : Planet.values()){//values返回枚举值
			//格式化输出
			System.out.printf("Weight on %s is %f%n",p,p.surfaceWeight(mass));
		}
		/*
		 * 输出结果：
		 *  Weight on MERCURY is 7558133.944223
			Weight on VENUS is 18101020.144543
			Weight on EARTH is 20000000.000000
			Weight on MARS is 7592079.939139
		 */
	}
}
