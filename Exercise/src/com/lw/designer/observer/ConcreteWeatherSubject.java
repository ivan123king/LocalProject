package com.lw.designer.observer;

import java.util.Observable;

/**
 * 天气目标具体实现类
 * @author lenovo
 *
 */
public class ConcreteWeatherSubject extends Observable{

		private String content;

		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
			//通知观察者目标对象改变
			this.setChanged();
			this.notifyObservers();
			
		}
		
}
