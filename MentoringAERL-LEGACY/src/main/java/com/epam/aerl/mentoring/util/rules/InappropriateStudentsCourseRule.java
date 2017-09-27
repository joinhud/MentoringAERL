package com.epam.aerl.mentoring.util.rules;

import com.epam.aerl.mentoring.entity.Student;

public class InappropriateStudentsCourseRule extends CompositeEmployerFilterRule {
    private int inappropriateCourse;

    public InappropriateStudentsCourseRule(final int inappropriateCourse) {
        this.inappropriateCourse = inappropriateCourse;
    }

    @Override
    public boolean isSatisfiedBy(final Student student) {
        boolean result = false;

        if (student != null) {
            result = inappropriateCourse != student.getCourse();
        }

        return result;
    }
}
