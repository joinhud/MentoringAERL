package com.epam.aerl.mentoring.util.rules;

import com.epam.aerl.mentoring.entity.Student;

public class NotEmployerFilterRule extends CompositeEmployerFilterRule {
    private EmployerFilterRule wrapper;

    public NotEmployerFilterRule(EmployerFilterRule wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public boolean isSatisfiedBy(Student student) {
        return !wrapper.isSatisfiedBy(student);
    }
}
