package com.epam.aerl.mentoring.util.rules;

public abstract class CompositeEmployerFilterRule implements EmployerFilterRule {
    @Override
    public EmployerFilterRule and(EmployerFilterRule other) {
        return new AndEmployerFilterRule(this, other);
    }

    @Override
    public EmployerFilterRule or(EmployerFilterRule other) {
        return new OrEmployerFilterRule(this, other);
    }

    @Override
    public EmployerFilterRule not() {
        return new NotEmployerFilterRule(this);
    }
}
