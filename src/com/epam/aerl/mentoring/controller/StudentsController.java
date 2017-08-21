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
	
	private EmployersGenerator employersGenerator = new EmployersGenerator();
	private StudentsGenerator studentsGenerator = new StudentsGenerator();
	
	private StudentsService service = new StudentsService();
	
	public void takeStudents() {
		try {
			List<EmployerFilter> employers = employersGenerator.generate();
			List<Student> students = studentsGenerator.generateStudents(STUDENTS_COUNT);
			
			service.takeStudentsFromUniversity(employers, students);
		} catch (ServiceException e) {
			Printer.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		} catch (StudentsGeneratorException e) {
			Printer.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		}
	}
	
}
