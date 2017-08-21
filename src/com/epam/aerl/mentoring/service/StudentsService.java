package com.epam.aerl.mentoring.service;

import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.EmplyerFilterException;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.Printer;

public class StudentsService {
	private static final String CAPTION = "Students which will remain:";
	
	private static final String NOT_FILTERED_STUDENTS_ERR = "Can not filter list of students.";
	
	public void takeStudentsFromUniversity(List<EmployerFilter> employers, List<Student> students) throws ServiceException {
		try {
			for(EmployerFilter employer : employers) {
				employer.takeAway(students);
			}
			
			printRemainStudents(students);
		} catch (EmplyerFilterException e) {
			throw new ServiceException(ErrorMessage.NOT_FILTERED_ERROR.getCode(), NOT_FILTERED_STUDENTS_ERR, e);
		}
	}
	
	private void printRemainStudents(List<Student> students) {
		Printer.printCaption(CAPTION);
		
		for(Student student : students) {
			Printer.printStudentData(student);
		}
	}
	
}
