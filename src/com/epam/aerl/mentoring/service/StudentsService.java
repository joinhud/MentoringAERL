package com.epam.aerl.mentoring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.filter.CompanyDirectorFilter;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.filter.MilitaryCommissioner;
import com.epam.aerl.mentoring.filter.ProfessorMathFilter;
import com.epam.aerl.mentoring.util.StudentsGenerator;

public class StudentsService {
	private static final int STUDENTS_COUNT = 50;
	private static final String RAMAIN_STUDENTS_MSG = "Students which will remain:";

	private List<Student> students;
	private List<EmployerFilter> employers = new ArrayList<>();
	
	public StudentsService() {
		employers.add(new ProfessorMathFilter());
		employers.add(new MilitaryCommissioner());
		employers.add(new CompanyDirectorFilter());
		
		try {
			StudentsGenerator generator = new StudentsGenerator();
			students = generator.generateStudents(STUDENTS_COUNT);
		} catch (StudentsGeneratorException e) {
			System.err.println(e);
			students = new ArrayList<>();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void takeStudentsFromUniversity() {
		for(EmployerFilter employer : employers) {
			employer.takeAway(students);
		}
		
		printRemainStudents();
	}
	
	private void printRemainStudents() {
		System.out.println(RAMAIN_STUDENTS_MSG);
		
		for(Student student : students) {
			System.out.println(student);
		}
	}
	
}
