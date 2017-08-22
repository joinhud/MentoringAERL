package com.epam.aerl.mentoring;

import com.epam.aerl.mentoring.controller.StudentsController;

public class Main {
	private static final StudentsController CONTROLLER = new StudentsController();
	
	public static void main(String[] args) {
		CONTROLLER.takeStudents();
	}

}
