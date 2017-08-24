package com.epam.aerl.mentoring.util.rules;

import com.epam.aerl.mentoring.entity.Student;

public interface EmployerFilterRule {
    boolean isSatisfiedBy(final Student student);
    EmployerFilterRule and(EmployerFilterRule other);
    EmployerFilterRule or(EmployerFilterRule other);
    EmployerFilterRule not();
}
