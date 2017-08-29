package com.epam.aerl.mentoring.entity;

public class StudentClassCriteria {
    private StudentRangeCriteria ageCriteria;
    private StudentRangeCriteria courseCriteria;
    private StudentMarksWrapper studentMarksWrapperCriteria;

    private StudentClassCriteria() {
    }

    public StudentRangeCriteria getAgeCriteria() {
        return ageCriteria;
    }

    public StudentRangeCriteria getCourseCriteria() {
        return courseCriteria;
    }

    public StudentMarksWrapper getStudentMarksWrapperCriteria() {
        return studentMarksWrapperCriteria;
    }

    public static StudentClassCriteriaBuilder newBuilder() {
        return new StudentClassCriteria().new StudentClassCriteriaBuilder();
    }

    public class StudentClassCriteriaBuilder {

        private StudentClassCriteriaBuilder() {
        }

        public StudentClassCriteriaBuilder ageCriteria(final StudentRangeCriteria newAgeCriteria) {
            StudentClassCriteria.this.ageCriteria = newAgeCriteria;
            return this;
        }

        public StudentClassCriteriaBuilder courseCriteria(final StudentRangeCriteria newCourseCriteria) {
            StudentClassCriteria.this.courseCriteria = newCourseCriteria;
            return this;
        }

        public StudentClassCriteriaBuilder studentMarksWrapperCriteria(
                final StudentMarksWrapper newStudentMarksWrapperCriteria) {
            StudentClassCriteria.this.studentMarksWrapperCriteria = newStudentMarksWrapperCriteria;
            return this;
        }

        public StudentClassCriteria build() {
            StudentClassCriteria criteria = new StudentClassCriteria();
            criteria.ageCriteria = StudentClassCriteria.this.ageCriteria;
            criteria.courseCriteria = StudentClassCriteria.this.courseCriteria;
            criteria.studentMarksWrapperCriteria = StudentClassCriteria.this.studentMarksWrapperCriteria;

            return criteria;
        }
    }
}
