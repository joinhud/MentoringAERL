package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentMarksCalculator;

public class CompanyDirectorFilter extends EmployerFilter {

	private static final int HIGHEST_MARK = 10;
	private static final int MIN_AGE = 22;
	private static final int MIN_COURSE = 3;
	private static final int MAX_COURSE = 4;
	private static final double AVERAGE_SCIENSE_MARK = 7.5;

	private static final String CAPTION = "A director of engineering company took following students:";
	
	@Override
	public boolean checkCriteria(Student student) {
		return checkHighestScore(student) 
				|| checkStudentsAge(student.getAge()) 
				&& checkStudentsCourse(student.getCourse())
				&& checkStudentsAverageSciensesMark(student);
	}

	@Override
	public void printEmployerCaption() {
		Printer.printCaption(CAPTION);	
	}

	private boolean checkHighestScore(Student student) {
		boolean result = false;
		
		if (student != null) {
			int mathMark = student.getMarks().get(Subject.MATH);
			int philosophyMark = student.getMarks().get(Subject.PHILOSOPHY);
			
			result = mathMark == philosophyMark && mathMark == HIGHEST_MARK;
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
			StudentMarksCalculator calculator = new StudentMarksCalculator();
			result = calculator.calculateAverageSciensesMark(student.getMarks()) >= AVERAGE_SCIENSE_MARK;
		}
		
		return result;
	}
}
