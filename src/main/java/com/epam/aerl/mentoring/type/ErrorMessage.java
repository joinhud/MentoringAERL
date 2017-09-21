package com.epam.aerl.mentoring.type;

public enum ErrorMessage {
	STUDENTS_NUMBER_ERROR("1", "Students haven't been correctly created. Please try again later."),
	FILE_ERROR("2", "Some internal errors is occurred. Please try again later."),
	NO_STUDENTS_ERROR("3", "Students haven't been correctly created. Please try again later."),
	OUTPUT_FILE_ERROR("4", "Some internal errors is occurred. Please try again later."),
	CONFLICTS_INPUT_CRITERIA("5", "Input criteria has conflict. Please check your input and try again.");
	
	private String code;
	private String message;
	
	ErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ErrorMessage getByCode(String code) {
		ErrorMessage result = null;
		
		for(ErrorMessage errorMessage : values()) {
			if (code.equals(errorMessage.code)) {
				result = errorMessage;
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
