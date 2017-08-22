package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.ErrorMessage;

public class Printer {

	public void printStudentData(final Student student) {
		System.out.println(student);
	}
	
	public void printCaption(final String caption) {
		System.out.println(caption);
	}
	
	public void printErrorMessage(final ErrorMessage message) {
		System.out.println(message.getMessage());
	}
}
