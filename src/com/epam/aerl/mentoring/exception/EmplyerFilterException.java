package com.epam.aerl.mentoring.exception;

public class EmplyerFilterException extends Exception {
	private String code;
	
	public EmplyerFilterException() {
		super();
	}

	public EmplyerFilterException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public EmplyerFilterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EmplyerFilterException(String arg0) {
		super(arg0);
	}

	public EmplyerFilterException(Throwable arg0) {
		super(arg0);
	}
	
	public EmplyerFilterException(String code, String arg0) {
		super(arg0);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
