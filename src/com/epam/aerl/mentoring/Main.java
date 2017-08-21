package com.epam.aerl.mentoring;

import com.epam.aerl.mentoring.controller.StudentsController;

public class Main {
	private static StudentsController controller = new StudentsController();
	
	public static void main(String[] args) {
		controller.takeStudents();
	}

}
