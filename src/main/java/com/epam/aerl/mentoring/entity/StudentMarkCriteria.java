package com.epam.aerl.mentoring.entity;

public class StudentMarkCriteria {
    private double minMark;
    private double maxMark;

    private StudentMarkCriteria(final double newMinMark, final double newMaxMark) {
        this.minMark = newMinMark;
        this.maxMark = newMaxMark;
    }

    public double getMinMark() {
        return minMark;
    }

    public double getMaxMark() {
        return maxMark;
    }

    public static class StudentMarkCriteriaBuilder {
        private double builderMinMark;
        private double builderMaxMark;

        public StudentMarkCriteriaBuilder(final double newMinMark, final double newMaxMark) {
            this.builderMinMark = newMinMark;
            this.builderMaxMark = newMaxMark;
        }

        public StudentMarkCriteriaBuilder minMark(final double newMinMark) {
            this.builderMinMark = newMinMark;
            return this;
        }

        public StudentMarkCriteriaBuilder maxMark(final double newMaxMark) {
            this.builderMaxMark = newMaxMark;
            return this;
        }

        public StudentMarkCriteria createMarkCriteria() {
            return new StudentMarkCriteria(builderMinMark, builderMaxMark);
        }
    }
}
