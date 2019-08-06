package com.lw.house.user.exception;

public class IllegalParamsException extends RuntimeException implements
		WithTypeException {

	private static final long serialVersionUID = -7553041176527389900L;
	
	public Type type;

	public IllegalParamsException(Type type, String msg) {
		super(msg);
		this.type = type;
	}

	public Type type() {
		return type;
	}

	public enum Type {
		WRONG_PAGE_NUM, WRONG_TYPE
	}
}
