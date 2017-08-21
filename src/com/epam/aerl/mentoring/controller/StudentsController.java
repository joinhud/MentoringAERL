package com.epam.aerl.mentoring.controller;

import com.epam.aerl.mentoring.service.StudentsService;

public class StudentsController {

	private StudentsService service = new StudentsService();
	
	public void takeStudents() {
		service.takeStudentsFromUniversity();
	}
	
}
