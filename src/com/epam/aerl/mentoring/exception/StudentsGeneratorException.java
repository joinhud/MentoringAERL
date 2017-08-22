package com.epam.aerl.mentoring.exception;

public class StudentsGeneratorException extends Exception {
	
	private String code;
	
	public StudentsGeneratorException() {
		super();
	}
	
	public StudentsGeneratorException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public StudentsGeneratorException(String arg0) {
		super(arg0);
	}
	

	public StudentsGeneratorException(Throwable arg0) {
		super(arg0);
	}

	public StudentsGeneratorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public StudentsGeneratorException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public String getCode() {
		return code;
	}
}
