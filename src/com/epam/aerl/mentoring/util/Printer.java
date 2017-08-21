package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.ErrorMessage;

public class Printer {

	public static void printStudentData(Student student) {
		System.out.println(student);
	}
	
	public static void printCaption(String caption) {
		System.out.println(caption);
	}
	
	public static void printErrorMessage(ErrorMessage message) {
		System.out.println(message.getMessage());
	}
}
