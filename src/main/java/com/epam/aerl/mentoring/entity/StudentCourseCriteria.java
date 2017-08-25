package com.epam.aerl.mentoring.entity;

public class StudentCourseCriteria {
    private int minCourse;
    private int maxCourse;

    public StudentCourseCriteria(int minCourse, int maxCourse) {
        this.minCourse = minCourse;
        this.maxCourse = maxCourse;
    }

    public int getMinCourse() {
        return minCourse;
    }

    public int getMaxCourse() {
        return maxCourse;
    }
}
