package com.epam.aerl.mentoring.util.rules;

import com.epam.aerl.mentoring.entity.Student;

public class AndEmployerFilterRule extends CompositeEmployerFilterRule {
    private EmployerFilterRule first;
    private EmployerFilterRule second;

    public AndEmployerFilterRule(final EmployerFilterRule first, final EmployerFilterRule second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfiedBy(final Student student) {
        return first.isSatisfiedBy(student) && second.isSatisfiedBy(student);
    }
}
