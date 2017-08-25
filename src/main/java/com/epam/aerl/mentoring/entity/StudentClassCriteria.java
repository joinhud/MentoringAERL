package com.epam.aerl.mentoring.entity;

public class StudentClassCriteria {
    private StudentAgeCriteria ageCriteria;
    private StudentCourseCriteria courseCriteria;
    private StudentMarksWrapper studentMarksWrapperCriteria;

    public StudentClassCriteria() {
    }

    public StudentClassCriteria(StudentAgeCriteria ageCriteria, StudentCourseCriteria courseCriteria, StudentMarksWrapper studentMarksWrapperCriteria) {
        this.ageCriteria = ageCriteria;
        this.courseCriteria = courseCriteria;
        this.studentMarksWrapperCriteria = studentMarksWrapperCriteria;
    }

    public StudentAgeCriteria getAgeCriteria() {
        return ageCriteria;
    }

    public void setAgeCriteria(StudentAgeCriteria ageCriteria) {
        this.ageCriteria = ageCriteria;
    }

    public StudentCourseCriteria getCourseCriteria() {
        return courseCriteria;
    }

    public void setCourseCriteria(StudentCourseCriteria courseCriteria) {
        this.courseCriteria = courseCriteria;
    }

    public StudentMarksWrapper getStudentMarksWrapperCriteria() {
        return studentMarksWrapperCriteria;
    }

    public void setStudentMarksWrapperCriteria(StudentMarksWrapper studentMarksWrapperCriteria) {
        this.studentMarksWrapperCriteria = studentMarksWrapperCriteria;
    }
}
