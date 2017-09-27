package com.epam.aerl.mentoring.type;

public enum Subject {
	MATH(true), PHILOSOPHY(true), PHYSICAL_EDUCATION(false);
	
	private boolean sciences;

	Subject(boolean sciences) {
		this.sciences = sciences;
	}

	public boolean isSciences() {
		return sciences;
	}
}
