package com.epam.aerl.mentoring.entity;

public class StudentAgeCriteria {
    private int minAge;
    private int maxAge;

    public StudentAgeCriteria(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
