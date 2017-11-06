package com.lw.designer.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.lw.designer.proxy.Car;
import com.lw.designer.proxy.Moveable;

public class Test {

		public static void main(String[] args) {
			Car car = new Car();
			InvocationHandler h = new TimeHandler(car);
			Class<?> cls = car.getClass();
			/*
			 * 动态创建代理类
			 * loader 类加载器
			 * interfaces 实现接口
			 * h invocationHandler类
			 */
//			Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
//			m.move();
			
			CarTimeProxy ctp = new CarTimeProxy(car);
			CarLogProxy clp = new CarLogProxy(ctp);
//			cls = ctp.getClass();
			InvocationHandler lh = new LogHandler(clp);//这里才是要真正代理的对象 car
			//这里传的cls就是为了返回一个Moveable的实例化对象，只要能实例化Moveable随便传什么都行
			Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(),lh);
			m.move();
		}
}
