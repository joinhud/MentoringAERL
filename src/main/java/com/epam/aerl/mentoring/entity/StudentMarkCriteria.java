package com.epam.aerl.mentoring.entity;

public class StudentMarkCriteria {
    private double minMark;
    private double maxMark;

    public StudentMarkCriteria(double minMark, double maxMark) {
        this.minMark = minMark;
        this.maxMark = maxMark;
    }

    public double getMinMark() {
        return minMark;
    }

    public double getMaxMark() {
        return maxMark;
    }
}
