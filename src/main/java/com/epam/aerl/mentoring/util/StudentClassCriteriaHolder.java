package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.*;
import com.epam.aerl.mentoring.type.StudentClass;
import com.epam.aerl.mentoring.type.Subject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudentClassCriteriaHolder {
    private static volatile StudentClassCriteriaHolder instance;

    private Map<StudentClass, StudentClassCriteria> classCriteria;

    private StudentClassCriteriaHolder() {
    }

    public static StudentClassCriteriaHolder getInstance() {
        StudentClassCriteriaHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (StudentClassCriteriaHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new StudentClassCriteriaHolder();
                }
            }
        }
        return localInstance;
    }

    public Map<StudentClass, StudentClassCriteria> receiveStudentClassCriteria() {
        if (classCriteria == null) {
            classCriteria = new HashMap<>();

            StudentClassCriteria mClassCriteria = new StudentClassCriteria();
            Map<Subject, StudentMarkCriteria> mClassMarksCriteria = new LinkedHashMap<>();
            mClassMarksCriteria.put(Subject.MATH, new StudentMarkCriteria(9, 10));
            StudentMarksWrapper mClassMarksWrapper = new StudentMarksWrapper();
            mClassMarksWrapper.setMarksCriteria(mClassMarksCriteria);
            mClassCriteria.setStudentMarksWrapperCriteria(mClassMarksWrapper);
            classCriteria.put(StudentClass.M, mClassCriteria);

            StudentClassCriteria yClassCriteria = new StudentClassCriteria();
            yClassCriteria.setAgeCriteria(new StudentAgeCriteria(18, 20));
            yClassCriteria.setCourseCriteria(new StudentCourseCriteria(1, 4));
            classCriteria.put(StudentClass.Y, yClassCriteria);

            StudentClassCriteria pClassCriteria = new StudentClassCriteria();
            Map<Subject, StudentMarkCriteria> pClassMarksCriteria = new LinkedHashMap<>();
            pClassMarksCriteria.put(Subject.MATH, new StudentMarkCriteria(10, 10));
            pClassMarksCriteria.put(Subject.PHILOSOPHY, new StudentMarkCriteria(10, 10));
            StudentMarksWrapper pClassMarksWrapper = new StudentMarksWrapper();
            pClassMarksWrapper.setMarksCriteria(pClassMarksCriteria);
            pClassCriteria.setStudentMarksWrapperCriteria(pClassMarksWrapper);
            pClassCriteria.setAgeCriteria(new StudentAgeCriteria(22, 24));
            pClassCriteria.setCourseCriteria(new StudentCourseCriteria(3, 5));
            classCriteria.put(StudentClass.P, pClassCriteria);

            StudentClassCriteria lClassCriteria = new StudentClassCriteria();
            Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> lClassGroupOpCriteria = new HashMap<>();
            lClassGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE, new StudentMarkCriteria(0, 6.4));
            StudentMarksWrapper lClassMarksWrapper = new StudentMarksWrapper();
            lClassMarksWrapper.setGroupOperationsCriteria(lClassGroupOpCriteria);
            lClassCriteria.setStudentMarksWrapperCriteria(lClassMarksWrapper);
            lClassCriteria.setCourseCriteria(new StudentCourseCriteria(3, 5));
            classCriteria.put(StudentClass.L, lClassCriteria);

            StudentClassCriteria bClassCriteria = new StudentClassCriteria();
            Map<Subject, StudentMarkCriteria> bClassMarksCriteria = new LinkedHashMap<>();
            bClassMarksCriteria.put(Subject.PHYSICAL_EDUCATION, new StudentMarkCriteria(9, 10));
            StudentMarksWrapper bClassMarksWrapper = new StudentMarksWrapper();
            bClassMarksWrapper.setMarksCriteria(bClassMarksCriteria);
            bClassCriteria.setStudentMarksWrapperCriteria(bClassMarksWrapper);
            bClassCriteria.setAgeCriteria(new StudentAgeCriteria(22, 24));
            bClassCriteria.setCourseCriteria(new StudentCourseCriteria(3, 5));
            classCriteria.put(StudentClass.B, bClassCriteria);

            StudentClassCriteria oClassCriteria = new StudentClassCriteria();
            oClassCriteria.setAgeCriteria(new StudentAgeCriteria(18, 18));
            oClassCriteria.setCourseCriteria(new StudentCourseCriteria(1, 1));
            classCriteria.put(StudentClass.O, oClassCriteria);

            StudentClassCriteria nClassCriteria = new StudentClassCriteria();
            Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> nClassGroupOpCriteria = new HashMap<>();
            nClassGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE, new StudentMarkCriteria(7.5, 8.5));
            StudentMarksWrapper nClassMarksWrapper = new StudentMarksWrapper();
            nClassMarksWrapper.setGroupOperationsCriteria(nClassGroupOpCriteria);
            nClassCriteria.setStudentMarksWrapperCriteria(nClassMarksWrapper);
            nClassCriteria.setAgeCriteria(new StudentAgeCriteria(22, 23));
            nClassCriteria.setCourseCriteria(new StudentCourseCriteria(3, 5));
            classCriteria.put(StudentClass.N, nClassCriteria);

            StudentClassCriteria iClassCriteria = new StudentClassCriteria();
            Map<Subject, StudentMarkCriteria> iClassMarksCriteria = new LinkedHashMap<>();
            iClassMarksCriteria.put(Subject.PHYSICAL_EDUCATION, new StudentMarkCriteria(0, 6));
            StudentMarksWrapper iClassMarksWrapper = new StudentMarksWrapper();
            iClassMarksWrapper.setMarksCriteria(iClassMarksCriteria);
            iClassCriteria.setStudentMarksWrapperCriteria(iClassMarksWrapper);
            iClassCriteria.setCourseCriteria(new StudentCourseCriteria(1, 1));
            classCriteria.put(StudentClass.I, iClassCriteria);
        }

        return classCriteria;
    }
}
