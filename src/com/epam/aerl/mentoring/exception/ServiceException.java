package com.epam.aerl.mentoring.exception;

public class ServiceException extends Exception {
	private String code;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
	}

	public ServiceException(String code, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.code = code;
	}

	public String getCode() {
		return code;
	}	
}
