package com.epam.aerl.mentoring.filter;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.rules.AppropriateStudentsMathMarkRule;
import com.epam.aerl.mentoring.util.rules.EmployerFilterRule;
import com.epam.aerl.mentoring.util.rules.InappropriateStudentsCourseRule;
import com.epam.aerl.mentoring.util.rules.StudentsAgeRangeRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("professorMathFilter")
public class ProfessorMathFilter extends EmployerFilter {

	private static final int MIN_STUDENT_AGE = 20;
	private static final int MAX_STUDENT_AGE = 24;
	private static final int MATH_MARK = 8;
	private static final int INAPPROPRIATE_COURSE = 5;
	
	private static final String CAPTION = "Employer Professor of Math took:";

	@Autowired
	@Qualifier("printer")
	private Printer printer;

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    @Override
	public boolean checkCriteria(final Student student) {
		boolean result = false;
		
		if (student != null) {
			final EmployerFilterRule checkAge = new StudentsAgeRangeRule(MIN_STUDENT_AGE, MAX_STUDENT_AGE);
			final EmployerFilterRule checkMark = new AppropriateStudentsMathMarkRule(MATH_MARK);
			final EmployerFilterRule checkCourse = new InappropriateStudentsCourseRule(INAPPROPRIATE_COURSE);

			result = checkAge
                    .and(checkMark)
                    .and(checkCourse)
                    .isSatisfiedBy(student);
		}
		
		return result;
	}

	@Override
	public void printEmployerCaption() {
        printer.printCaption(CAPTION);
	}
}
