package com.epam.aerl.mentoring.entity;

import java.util.Iterator;
import java.util.List;

public class CompanyDirector implements Employer {

	private static final int HIGHEST_MARK = 10;
	private static final int MIN_AGE = 22;
	private static final int MIN_COURSE = 3;
	private static final int MAX_COURSE = 4;
	private static final double AVERAGE_SCIENSE_MARK = 7.5;
	private static final int SUBJECTS_COUNT = 2;

	private static final String TOOK_MSG = "A director of engineering company took following students:";
	
	@Override
	public void takeAway(List<Student> students) {
		if (students != null) {
			Iterator<Student> iterator = students.iterator();
			
			System.out.println(TOOK_MSG);
			
			while(iterator.hasNext()) {
				Student student = iterator.next();
				
				if (checkHighestScore(student) 
						|| checkStudentsAge(student.getAge()) 
						&& checkStudentsCourse(student.getCourse())
						&& checkStudentsAverageSciensesMark(student)) {
					System.out.println(student);
					iterator.remove();
				}
				
			}
		}
	}

	private boolean checkHighestScore(Student student) {
		boolean result = false;
		
		if (student != null) {
			result = student.getMathMark() == student.getPhilosophyMark() && student.getMathMark() == HIGHEST_MARK;
		}
		
		return result;
	}
	
	private boolean checkStudentsAge(int studentsAge) {
		return studentsAge >= MIN_AGE;
	}
	
	private boolean checkStudentsCourse(int studentsCourse) {
		return studentsCourse >= MIN_COURSE && studentsCourse <= MAX_COURSE;
	}
	
	private boolean checkStudentsAverageSciensesMark(Student student) {
		boolean result = false;
		
		if (student != null) {
			result = ((student.getMathMark() + student.getPhilosophyMark()) / SUBJECTS_COUNT) >= AVERAGE_SCIENSE_MARK;
		}
		
		return result;
	}
}
