package com.lw.designer.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 目标对象 他知道观察它的观察者，并提供注册和删除观察者的方法
 * @author lenovo
 *
 */
public class Subject {
	//注册了的观察者
	private List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * 添加注册了的观察者
	 * @param observer
	 */
	public void attach(Observer observer){
		observers.add(observer);
	}
	
	/**
	 * 删除注册了的观察者
	 * @param observer
	 */
	public void detach(Observer observer){
		observers.remove(observer);
	}
	
	/**
	 * 通知观察者目标对象发生改变
	 */
	protected void notifyObservers(){
		for(Observer o:observers){
			o.update(this);
		}
	}
}
