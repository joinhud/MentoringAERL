package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.util.Printer;

public class ProfessorMathFilter extends EmployerFilter {

	private static final int MIN_STUDENT_AGE = 20;
	private static final int MAX_STUDENT_AGE = 24;
	private static final int MATH_MARK = 8;
	private static final int INAPPROPRIATE_COURSE = 5;
	
	private static final String CAPTION = "A proffessor of Math took following students:";
	
	@Override
	public boolean checkCriteria(Student student) {
		return checkAge(student.getAge()) && checkMark(student.getMarks().get(Subject.MATH)) && checkCourse(student.getCourse());
	}

	@Override
	public void printEmployerCaption() {
		Printer.printCaption(CAPTION);
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
