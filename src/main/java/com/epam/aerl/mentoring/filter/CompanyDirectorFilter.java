package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentMarksCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("companyDirectorFilter")
public class CompanyDirectorFilter extends EmployerFilter {

	private static final int HIGHEST_MARK = 10;
	private static final int MIN_AGE = 22;
	private static final int MIN_COURSE = 3;
	private static final int MAX_COURSE = 4;
	private static final double AVERAGE_SCIENCES_MARK = 7.5;

	private static final String CAPTION = "Employer a director of engineering company took:";

	@Autowired
    @Qualifier("printer")
	private Printer printer;

	@Autowired
    @Qualifier("studentMarksCalculator")
	private StudentMarksCalculator calculator;

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public void setCalculator(StudentMarksCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
	public boolean checkCriteria(final Student student) {
	    boolean result = false;

	    if (student != null) {
	        result = checkHighestScore(student)
                    || checkStudentsAge(student.getAge())
                    && checkStudentsCourse(student.getCourse())
                    && checkStudentsAverageSciencesMark(student);
        }

		return result;
	}

	@Override
	public void printEmployerCaption() {
		printer.printCaption(CAPTION);
	}

	private boolean checkHighestScore(final Student student) {
		boolean result = false;
		
		if (student != null) {
			final Integer mathMark = student.getMarks().get(Subject.MATH);
			final Integer philosophyMark = student.getMarks().get(Subject.PHILOSOPHY);
			
			if (mathMark != null && philosophyMark != null) {
				result = mathMark.equals(philosophyMark) && mathMark == HIGHEST_MARK;
			}
		}
		
		return result;
	}
	
	private boolean checkStudentsAge(final int studentsAge) {
		return studentsAge >= MIN_AGE;
	}
	
	private boolean checkStudentsCourse(final int studentsCourse) {
		return studentsCourse >= MIN_COURSE && studentsCourse <= MAX_COURSE;
	}
	
	private boolean checkStudentsAverageSciencesMark(final Student student) {
		boolean result = false;
		
		if (student != null) {
			final Double averageSciencesMark = calculator.calculateAverageSciencesMark(student.getMarks());
			
			if (averageSciencesMark != null) {
				result = averageSciencesMark >= AVERAGE_SCIENCES_MARK;
			}
		}
		
		return result;
	}
}
