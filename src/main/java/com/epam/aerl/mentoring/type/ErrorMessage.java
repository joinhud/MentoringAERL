package com.epam.aerl.mentoring.type;

public enum ErrorMessage {
	STUDENTS_NUMBER_ERROR("1", "Students haven't been correctly created. Please try again later."),
	PROPERTIES_FILE_ERROR("2", "Some internal errors is occured. Please try again later."),
	NO_STUDENTS_ERROR("3", "Students haven't been correctly created. Please try again later.");
	
	private String code;
	private String message;
	
	ErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ErrorMessage getByCode(String code) {
		ErrorMessage result = null;
		
		for(ErrorMessage message : values()) {
			if (code.equals(message.code)) {
				result = null;
			}
		}
		
		return result;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
