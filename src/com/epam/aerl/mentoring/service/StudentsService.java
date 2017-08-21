package com.epam.aerl.mentoring.service;

import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.EmplyerFilterException;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.EmployersGenerator;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentsGenerator;

public class StudentsService {
	private static final int STUDENTS_COUNT = 50;
	private static final String CAPTION = "Students which will remain:";
	
	private static final String NOT_GENERATED_STUDENTS_ERR = "Can not generate students.";
	private static final String NOT_FILTERED_STUDENTS_ERR = "Can not filter list of students.";
	
	private EmployersGenerator employersGenerator = new EmployersGenerator();
	private StudentsGenerator studentsGenerator = new StudentsGenerator();
	
	public void takeStudentsFromUniversity() throws ServiceException {
		try {
			List<EmployerFilter> employers = employersGenerator.generate();
			List<Student> students = studentsGenerator.generateStudents(STUDENTS_COUNT);
			
			for(EmployerFilter employer : employers) {
				employer.takeAway(students);
			}
			
			printRemainStudents(students);
		} catch (StudentsGeneratorException e) {
			throw new ServiceException(ErrorMessage.NOT_GENRATE_ERROR.getCode(), NOT_GENERATED_STUDENTS_ERR, e);
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
