package com.epam.aerl.mentoring.filter;

import java.util.Iterator;
import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.EmplyerFilterException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.Printer;

public abstract class EmployerFilter {
	private static final String EMPTY_STUDENTS_ERR_MSG = "List of students is empty or null.";
	
	public void takeAway(List<Student> students) throws EmplyerFilterException {
		if (students != null && !students.isEmpty()) {
			Iterator<Student> iterator = students.iterator();
			
			printEmployerCaption();
			
			while(iterator.hasNext()) {
				Student student = iterator.next();
				
				if (checkCriteria(student)) {
					Printer.printStudentData(student);
					iterator.remove();
				}
				
			}
		} else {
			throw new EmplyerFilterException(ErrorMessage.NO_STUDENTS_ERROR.getCode(), EMPTY_STUDENTS_ERR_MSG);
		}
	}
	
	public abstract boolean checkCriteria(Student student);
	
	public abstract void printEmployerCaption();
}
