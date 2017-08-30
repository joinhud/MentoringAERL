package com.epam.aerl.mentoring;

import com.epam.aerl.mentoring.controller.StudentsController;

public class Main {
	private static final int FIRST_ARG = 0;
	private static final StudentsController CONTROLLER = new StudentsController();
	
	public static void main(String[] args) {
		CONTROLLER.takeStudents(args[FIRST_ARG]);
	}

}
