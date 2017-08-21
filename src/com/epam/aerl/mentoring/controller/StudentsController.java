package com.epam.aerl.mentoring.controller;

import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.service.StudentsService;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.Printer;

public class StudentsController {

	private StudentsService service = new StudentsService();
	
	public void takeStudents() {
		try {
			service.takeStudentsFromUniversity();
		} catch (ServiceException e) {
			Printer.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		}
	}
	
}
