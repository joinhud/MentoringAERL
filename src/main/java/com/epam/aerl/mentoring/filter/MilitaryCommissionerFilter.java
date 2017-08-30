package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentMarksCalculator;

public class MilitaryCommissionerFilter extends EmployerFilter {

	private static final int FIRST_COURSE_STUDENT_MIN_AGE = 18;
	private static final int FIRST_COURSE_STUDENT_MAX_AGE = 20;
	private static final int AVERAGE_MARK = 6;
	
	private static final int FIFTH_COURSE_STUDENT_MIN_AGE = 21;
	private static final int FIFTH_COURSE_STUDENT_MAX_AGE = 24;
	private static final int APPROPRIATE_PHYSICAL_EDUCATION_MARK = 9;
	
	private static final String CAPTION = "Employer a military Commissioner took:";
	
	private Printer printer = new Printer();
	private StudentMarksCalculator calculator = new StudentMarksCalculator();
	
	@Override
	public boolean checkCriteria(final Student student) {
		boolean result = false;

		if (student != null) {
		    result = checkFirstYearStudent(student) || checkFifthYearStudent(student);
        }

		return result;
	}

	@Override
	public void printEmployerCaption() {
		printer.printCaption(CAPTION);
	}

	private boolean checkFirstYearStudent(final Student student) {
		boolean result = false;
		
		if (student != null) {
			result = checkFirstYearStudentAge(student.getAge()) && checkAverageMark(student);
		}
		
		return result;
	}
	
	private boolean checkFirstYearStudentAge(final int studentsAge) {
		return studentsAge >= FIRST_COURSE_STUDENT_MIN_AGE && studentsAge <= FIRST_COURSE_STUDENT_MAX_AGE;
	}
	
	private boolean checkAverageMark(final Student student) {
		boolean result = false;
		
		if (student != null) {
			final Integer averageMark = calculator.calculateAverageMark(student.getMarks());
			
			if (averageMark != null) {
				result = averageMark < AVERAGE_MARK;
			}
		}
			
		return result;
	}

	private boolean checkFifthYearStudent(final Student student) {
		boolean result = false;
		
		if (student != null) {
			final Integer physicalEducationMark = student.getMarks().get(Subject.PHYSICAL_EDUCATION);
			
			if (physicalEducationMark != null) {
				result = checkFifthYearStudentAge(student.getAge()) && checkPhysicalEducationMark(physicalEducationMark);
			}
		}
		
		return result;
	}
	
	private boolean checkFifthYearStudentAge(final int studentsAge) {
		return studentsAge >= FIFTH_COURSE_STUDENT_MIN_AGE && studentsAge <= FIFTH_COURSE_STUDENT_MAX_AGE;
	}
	
	private boolean checkPhysicalEducationMark(final int mark) {
		return mark >= APPROPRIATE_PHYSICAL_EDUCATION_MARK;
	}
	
}
