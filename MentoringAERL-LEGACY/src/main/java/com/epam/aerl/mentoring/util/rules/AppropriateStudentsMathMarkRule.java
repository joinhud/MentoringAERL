package com.epam.aerl.mentoring.util.rules;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.type.Subject;

public class AppropriateStudentsMathMarkRule extends CompositeEmployerFilterRule {
    private int mathMark;

    public AppropriateStudentsMathMarkRule(final int mathMark) {
        this.mathMark = mathMark;
    }

    @Override
    public boolean isSatisfiedBy(final Student student) {
        boolean result = false;

        if (student != null) {
            final Integer studentsMathMark = student.getMarks().get(Subject.MATH);

            if (studentsMathMark != null) {
                result = studentsMathMark >= mathMark;
            }
        }

        return result;
    }
}
