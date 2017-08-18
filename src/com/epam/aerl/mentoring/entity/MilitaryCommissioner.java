package com.epam.aerl.mentoring.entity;

import java.util.Iterator;
import java.util.List;

public class MilitaryCommissioner implements Employer {

	private static final int FIRST_COURSE_STUDENT_MIN_AGE = 18;
	private static final int FIRST_COURSE_STUDENT_MAX_AGE = 20;
	private static final int EVARAGE_MARK = 6;
	
	private static final int FIFTH_COURSE_STUDENT_MIN_AGE = 21;
	private static final int FIFTH_COURSE_STUDENT_MAX_AGE = 24;
	private static final int APPROPRIATE_PHISICAL_EDUCATION_MARK = 9;
	
	private static final int STUDENTS_MARKS_COUNT = 3;
	
	private static final String TOOK_MSG = "A military Commissioner took following students:";

	@Override
	public void takeAway(List<Student> students) {
		if (students != null) {
			Iterator<Student> iterator = students.iterator();
			
			System.out.println(TOOK_MSG);
			
			while(iterator.hasNext()) {
				Student student = iterator.next();
				
				if (checkFirstYearStudent(student) || checkFifthYearStudent(student)) {
					System.out.println(student);
					iterator.remove();
				}
				
			}
		}
	}
	
	private boolean checkFirstYearStudent(Student student) {
		boolean result = false;
		
		if (student != null) {
			result = checkFirstYearStudentAge(student.getAge()) && checkEverageMark(student);
		}
		
		return result;
	}
	
	private boolean checkFirstYearStudentAge(int studentsAge) {
		return studentsAge >= FIRST_COURSE_STUDENT_MIN_AGE && studentsAge <= FIRST_COURSE_STUDENT_MAX_AGE;
	}
	
	private boolean checkEverageMark(Student student) {
		return calculateStudentsAverageMark(student) < EVARAGE_MARK;
	}
	
	private double calculateStudentsAverageMark(Student student) {
		return (student.getMathMark() + student.getPhilosophyMark() + student.getPhysicalEducationMark()) / STUDENTS_MARKS_COUNT;
	}

	private boolean checkFifthYearStudent(Student student) {
		boolean result = false;
		
		if (student != null) {
			result = checkFifthYearStudentAge(student.getAge()) && checkPhisicalEducationMark(student.getPhysicalEducationMark());
		}
		
		return result;
	}
	
	private boolean checkFifthYearStudentAge(int studentsAge) {
		return studentsAge >= FIFTH_COURSE_STUDENT_MIN_AGE && studentsAge <= FIFTH_COURSE_STUDENT_MAX_AGE;
	}
	
	private boolean checkPhisicalEducationMark(int mark) {
		return mark >= APPROPRIATE_PHISICAL_EDUCATION_MARK;
	}
	
}
