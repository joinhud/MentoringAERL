package com.epam.aerl.mentoring.entity;

public class StudentAgeCriteria {
    private final int minAge;
    private final int maxAge;

    private StudentAgeCriteria(final int newMinAge, final int newMaxAge) {
        this.minAge = newMinAge;
        this.maxAge = newMaxAge;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public static class StudentAgeCriteriaBuilder {
        private int builderMinAge;
        private int builderMaxAge;

        public StudentAgeCriteriaBuilder(final int builderMinAge, final int builderMaxAge) {
            this.builderMinAge = builderMinAge;
            this.builderMaxAge = builderMaxAge;
        }

        public StudentAgeCriteriaBuilder minAge(final int newMinAge)
        {
            this.builderMinAge = newMinAge;
            return this;
        }

        public StudentAgeCriteriaBuilder maxAge(final int newMaxAge)
        {
            this.builderMaxAge = newMaxAge;
            return this;
        }

        public StudentAgeCriteria createAgeCriteria() {
            return new StudentAgeCriteria(builderMinAge, builderMaxAge);
        }
    }
}
