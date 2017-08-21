package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentMarksCalculator;

public class MilitaryCommissionerFilter extends EmployerFilter {

	private static final int FIRST_COURSE_STUDENT_MIN_AGE = 18;
	private static final int FIRST_COURSE_STUDENT_MAX_AGE = 20;
	private static final int EVARAGE_MARK = 6;
	
	private static final int FIFTH_COURSE_STUDENT_MIN_AGE = 21;
	private static final int FIFTH_COURSE_STUDENT_MAX_AGE = 24;
	private static final int APPROPRIATE_PHISICAL_EDUCATION_MARK = 9;
	
	private static final String CAPTION = "A military Commissioner took following students:";

	@Override
	public boolean checkCriteria(Student student) {
		return checkFirstYearStudent(student) || checkFifthYearStudent(student);
	}

	@Override
	public void printEmployerCaption() {
		Printer.printCaption(CAPTION);
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
		StudentMarksCalculator calculator = new StudentMarksCalculator();
			
		return calculator.calculateAvarageMark(student.getMarks()) < EVARAGE_MARK;
	}

	private boolean checkFifthYearStudent(Student student) {
		boolean result = false;
		
		if (student != null) {
			result = checkFifthYearStudentAge(student.getAge()) && checkPhisicalEducationMark(student.getMarks().get(Subject.PHYSICAL_EDUCATION));
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
