package com.epam.aerl.mentoring.type;

public enum Subject {
	MATH(true), PHILOSOPHY(true), PHYSICAL_EDUCATION(false);
	
	private boolean scienses;

	Subject(boolean scienses) {
		this.scienses = scienses;
	}

	public boolean isScienses() {
		return scienses;
	}
}
