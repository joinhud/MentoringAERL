package com.epam.aerl.mentoring.entity;

public class StudentClassCriteria {
    private final StudentAgeCriteria ageCriteria;
    private final StudentCourseCriteria courseCriteria;
    private final StudentMarksWrapper studentMarksWrapperCriteria;

    private StudentClassCriteria() {
        this.ageCriteria = null;
        this.courseCriteria = null;
        this.studentMarksWrapperCriteria = null;
    }

    private StudentClassCriteria(final StudentAgeCriteria newAgeCriteria,
                                 final StudentCourseCriteria newCourseCriteria,
                                 final StudentMarksWrapper newStudentMarksWrapperCriteria) {
        this.ageCriteria = newAgeCriteria;
        this.courseCriteria = newCourseCriteria;
        this.studentMarksWrapperCriteria = newStudentMarksWrapperCriteria;
    }

    public StudentAgeCriteria getAgeCriteria() {
        return ageCriteria;
    }

    public StudentCourseCriteria getCourseCriteria() {
        return courseCriteria;
    }

    public StudentMarksWrapper getStudentMarksWrapperCriteria() {
        return studentMarksWrapperCriteria;
    }

    public static class StudentClassCriteriaBuilder {
        private StudentAgeCriteria builderAgeCriteria;
        private StudentCourseCriteria builderCourseCriteria;
        private StudentMarksWrapper builderStudentMarksWrapperCriteria;

        public StudentClassCriteriaBuilder() {
            this.builderAgeCriteria = null;
            this.builderCourseCriteria = null;
            this.builderStudentMarksWrapperCriteria = null;
        }

        public StudentClassCriteriaBuilder ageCriteria(final StudentAgeCriteria newAgeCriteria) {
            this.builderAgeCriteria = newAgeCriteria;
            return this;
        }

        public StudentClassCriteriaBuilder courseCriteria(final StudentCourseCriteria newCourseCriteria) {
            this.builderCourseCriteria = newCourseCriteria;
            return this;
        }

        public StudentClassCriteriaBuilder studentMarksWrapperCriteria(
                final StudentMarksWrapper newStudentMarksWrapperCriteria) {
            this.builderStudentMarksWrapperCriteria = newStudentMarksWrapperCriteria;
            return this;
        }

        public StudentClassCriteria createClassCriteria() {
            return new StudentClassCriteria(builderAgeCriteria,
                    builderCourseCriteria, builderStudentMarksWrapperCriteria);
        }
    }
}
