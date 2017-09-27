package com.epam.aerl.mentoring.exception;

public class ServiceLayerException extends Exception {
	private static final long serialVersionUID = 90457033106651484L;
	
	private String code;
	
	public ServiceLayerException() {
		super();
	}

	public ServiceLayerException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ServiceLayerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

    public ServiceLayerException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceLayerException(String arg0) {
		super(arg0);
	}

	public ServiceLayerException(Throwable arg0) {
		super(arg0);
	}

	public ServiceLayerException(String code, String arg0) {
		super(arg0);
		this.code = code;
	}

	public String getCode() {
		return code;
	}	
}
