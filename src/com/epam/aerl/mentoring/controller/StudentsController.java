package com.epam.aerl.mentoring.controller;

import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.service.StudentsService;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.EmployersGenerator;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentsGenerator;

public class StudentsController {
	private static final int STUDENTS_COUNT = 50;
	
	private static final EmployersGenerator EMPLOYERS_GENERATOR = new EmployersGenerator();
	private static final StudentsGenerator STUDENTS_GENERATOR = new StudentsGenerator();
	
	private static final StudentsService SERVICE = new StudentsService();
	private static final Printer PRINTER = new Printer();
	
	public void takeStudents() {
		STUDENTS_GENERATOR.init();
		
		try {
			List<EmployerFilter> employers = EMPLOYERS_GENERATOR.generate();
			List<Student> students = STUDENTS_GENERATOR.generateStudents(STUDENTS_COUNT);
			
			SERVICE.takeStudentsFromUniversity(employers, students);
		} catch (ServiceException e) {
			PRINTER.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		} catch (StudentsGeneratorException e) {
			PRINTER.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		}
	}
	
}
