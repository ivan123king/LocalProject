package com.lw.designer.observer;

/**
 * 具体的目标对象
 * @author lenovo
 *
 */
public class ConcreteSubject extends Subject{

		private String subjectState;//目标对象的状态

		public String getSubjectState() {
			return subjectState;
		}

		public void setSubjectState(String subjectState) {
			this.subjectState = subjectState;
			this.notifyObservers();
		}
		
		
}
