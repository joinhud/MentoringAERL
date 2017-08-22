package com.epam.aerl.mentoring.exception;

public class BusinessLogicException extends RuntimeException {
	private static final long serialVersionUID = 1714328157307165909L;
	
	private String code;
	
	public BusinessLogicException() {
		super();
	}
	
	public BusinessLogicException(String arg0) {
		super(arg0);
	}

	public BusinessLogicException(Throwable arg0) {
		super(arg0);
	}

	public BusinessLogicException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessLogicException(String code, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.code = code;
	}

	public BusinessLogicException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public String getCode() {
		return code;
	}
}
