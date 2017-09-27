package com.epam.aerl.mentoring.type;

public enum ErrorMessage {
	STUDENTS_NUMBER_ERROR("1", "Students haven't been correctly created. Please try again later."),
	FILE_ERROR("2", "Some internal errors is occurred. Please try again later."),
	NO_STUDENTS_ERROR("3", "Students haven't been correctly created. Please try again later."),
	OUTPUT_FILE_ERROR("4", "Some internal errors is occurred. Please try again later."),
	CONFLICTS_INPUT_CRITERIA("5", "Input criteria has conflict. Please check your input and try again."),
	NO_VALID_STUDENT_DATA("6", "No valid input data. Student's name or student's surname is more than 20 symbols. Please check your input student's data."),
  NO_VALID_UNIVERSITY_DATA("7", "No valid input data. UniversityDTO name more than 15 symbols or university description more than 300 symbols. Please check your input university data."),
	GENERATION_ERROR("8", "Some internal errors is occurred. Please try again later."),
  FAILED_ACCESS_TO_DB("9", "Can not write data to database. Please try again later."),
  INCORRECT_STUDENTS_GENERATION_NUMBER("10", "Generation number of students must be less than 30. Please check your input criteria."),
  INCORRECT_UNIVERSITY_ID("11", "This id does not exist in the database. Please check your input and try again later.");
	
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
