package com.epam.aerl.mentoring.util.rules;

import com.epam.aerl.mentoring.entity.Student;

public class StudentsAgeRangeRule extends CompositeEmployerFilterRule {
    private int minAge;
    private int maxAge;

    public StudentsAgeRangeRule(final int minAge, final int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    @Override
    public boolean isSatisfiedBy(final Student student) {
        boolean result = false;

        if (student != null) {
            int studentsAge = student.getAge();

            result = studentsAge >= minAge && studentsAge <= maxAge;
        }

        return result;
    }
}
