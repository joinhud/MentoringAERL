package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.util.Printer;

public class ProfessorMathFilter extends EmployerFilter {

	private static final int MIN_STUDENT_AGE = 20;
	private static final int MAX_STUDENT_AGE = 24;
	private static final int MATH_MARK = 8;
	private static final int INAPPROPRIATE_COURSE = 5;
	
	private static final String CAPTION = "A professor of Math took following students:";
	
	private static final Printer PRINTER = new Printer();
	
	@Override
	public boolean checkCriteria(final Student student) {
		boolean result = false;
		
		if (student != null) {
			final Integer mathMark = student.getMarks().get(Subject.MATH);
			
			if (mathMark != null) {
				result = checkAge(student.getAge()) && checkMark(mathMark) && checkCourse(student.getCourse());
			}
		}
		
		return result;
	}

	@Override
	public void printEmployerCaption() {
		PRINTER.printCaption(CAPTION);
	}

	private boolean checkAge(final int studentsAge) {
		return studentsAge >= MIN_STUDENT_AGE && studentsAge <= MAX_STUDENT_AGE;
	}
	
	private boolean checkMark(final int studentsMark) {
		return studentsMark >= MATH_MARK;
	}
	
	private boolean checkCourse(final int course) {
		return course < INAPPROPRIATE_COURSE;
	}

}
