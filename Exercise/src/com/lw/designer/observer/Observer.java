package com.lw.designer.observer;

/**
 * 观察者接口，定义一个更新的接口给那些目标发生改变的对象
 * @author lenovo
 *
 */
public interface Observer {
	public void update(Subject subject);
}
