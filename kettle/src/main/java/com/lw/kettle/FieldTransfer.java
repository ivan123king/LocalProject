package com.lw.kettle;

/**
 * 字段转换类
 * 
 * @author lenovo
 *
 */
public class FieldTransfer {

	private String field;

	private String src;

	private String target;

	private boolean regEx = false;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isRegEx() {
		return regEx;
	}

	public void setRegEx(boolean regEx) {
		this.regEx = regEx;
	}

}
