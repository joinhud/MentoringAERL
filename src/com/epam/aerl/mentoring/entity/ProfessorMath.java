package com.epam.aerl.mentoring.entity;

import java.util.Iterator;
import java.util.List;

public class ProfessorMath implements Employer {

	private static final int MIN_STUDENT_AGE = 20;
	private static final int MAX_STUDENT_AGE = 24;
	private static final int MATH_MARK = 8;
	private static final int INAPPROPRIATE_COURSE = 5;
	
	private static final String TOOK_MSG = "A proffessor of Math took following students:";

	@Override
	public void takeAway(List<Student> students) {
		if (students != null) {
			Iterator<Student> iterator = students.iterator();
			
			System.out.println(TOOK_MSG);
			
			while(iterator.hasNext()) {
				Student student = iterator.next();
				
				if (checkAge(student.getAge()) && checkMark(student.getMathMark()) && checkCourse(student.getCourse())) {
					System.out.println(student);
					iterator.remove();
				}
				
			}
		}
	}
	
	private boolean checkAge(int studentsAge) {
		return studentsAge >= MIN_STUDENT_AGE && studentsAge <= MAX_STUDENT_AGE;
	}
	
	private boolean checkMark(int studentsMark) {
		return studentsMark >= MATH_MARK;
	}
	
	private boolean checkCourse(int course) {
		return course < INAPPROPRIATE_COURSE;
	}

}
