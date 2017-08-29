package com.epam.aerl.mentoring.entity;

public class StudentRangeCriteria {
    private double min;
    private double max;

    private StudentRangeCriteria() {
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public static StudentRangeCriteriaBuilder newBuilder() {
        return new StudentRangeCriteria().new StudentRangeCriteriaBuilder();
    }

    public class StudentRangeCriteriaBuilder {
        private StudentRangeCriteriaBuilder() {
        }

        public StudentRangeCriteriaBuilder min(final double newMin) {
            StudentRangeCriteria.this.min = newMin;
            return this;
        }

        public StudentRangeCriteriaBuilder max(final double newMax) {
            StudentRangeCriteria.this.max = newMax;
            return this;
        }

        public StudentRangeCriteria build() {
            StudentRangeCriteria criteria = new StudentRangeCriteria();
            criteria.min = StudentRangeCriteria.this.min;
            criteria.max = StudentRangeCriteria.this.max;

            return criteria;
        }
    }
}
