package com.epam.aerl.mentoring.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.aerl.mentoring.entity.CompanyDirector;
import com.epam.aerl.mentoring.entity.Employer;
import com.epam.aerl.mentoring.entity.MilitaryCommissioner;
import com.epam.aerl.mentoring.entity.ProfessorMath;
import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.util.StudentsGenerator;

public class StudentsService {
	private static final int STUDENTS_COUNT = 50;
	private static final String RAMAIN_STUDENTS_MSG = "Students which will remain:";

	private List<Student> students;
	private List<Employer> employers = new ArrayList<>();
	
	public StudentsService() {
		employers.add(new ProfessorMath());
		employers.add(new MilitaryCommissioner());
		employers.add(new CompanyDirector());
		
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
		for(Employer employer : employers) {
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
