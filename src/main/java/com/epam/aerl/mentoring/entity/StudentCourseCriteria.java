package com.epam.aerl.mentoring.entity;

public class StudentCourseCriteria {
    private final int minCourse;
    private final int maxCourse;

    private StudentCourseCriteria(final int newMinCourse, final int newMaxCourse) {
        this.minCourse = newMinCourse;
        this.maxCourse = newMaxCourse;
    }

    public int getMinCourse() {
        return minCourse;
    }

    public int getMaxCourse() {
        return maxCourse;
    }

    public static class StudentCourseCriteriaBuilder {
        private int builderMinCourse;
        private int builderMaxCourse;

        public StudentCourseCriteriaBuilder(final int newMinCourse, final int newMaxCourse) {
            this.builderMinCourse = newMinCourse;
            this.builderMaxCourse = newMaxCourse;
        }

        public StudentCourseCriteriaBuilder minCourse(final int newMinCourse) {
            this.builderMinCourse = newMinCourse;
            return this;
        }

        public StudentCourseCriteriaBuilder maxCourse(final int newMaxCourse) {
            this.builderMaxCourse = newMaxCourse;
            return this;
        }

        public StudentCourseCriteria createCourseCriteria() {
            return new StudentCourseCriteria(builderMinCourse, builderMaxCourse);
        }
    }
}
