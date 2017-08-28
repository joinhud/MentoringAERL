package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.*;
import com.epam.aerl.mentoring.type.Subject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StudentClassCriteriaHolder {
    private static final String M_CLASS = "M";
    private static final String Y_CLASS = "Y";
    private static final String P_CLASS = "P";
    private static final String L_CLASS = "L";
    private static final String B_CLASS = "B";
    private static final String O_CLASS = "O";
    private static final String N_CLASS = "N";
    private static final String I_CLASS = "I";
    private static final String RAND_CLASS = "rand";

    private static volatile StudentClassCriteriaHolder instance;

    private Map<String, StudentClassCriteria> classCriteria;

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

    public Map<String, StudentClassCriteria> receiveStudentClassCriteria() {
        if (classCriteria == null) {
            classCriteria = new HashMap<>();

            Map<Subject, StudentMarkCriteria> classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.MATH,
                    new StudentMarkCriteria
                            .StudentMarkCriteriaBuilder(9, 10)
                            .createMarkCriteria()
            );

            classCriteria.put(M_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            new StudentMarksWrapper
                                    .StudentMarksWrapperBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .createMarksWrapper()
                    ).createClassCriteria()
            );

            classCriteria.put(Y_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .ageCriteria(
                            new StudentAgeCriteria
                                    .StudentAgeCriteriaBuilder(18, 20)
                                    .createAgeCriteria()
                    ).courseCriteria(
                            new StudentCourseCriteria
                                    .StudentCourseCriteriaBuilder(1, 4)
                                    .createCourseCriteria()
                    ).createClassCriteria()
            );

            classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.MATH, new StudentMarkCriteria
                    .StudentMarkCriteriaBuilder(10, 10).createMarkCriteria()
            );
            classMarksCriteria.put(Subject.PHILOSOPHY, new StudentMarkCriteria
                    .StudentMarkCriteriaBuilder(10, 10).createMarkCriteria()
            );

            classCriteria.put(P_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            new StudentMarksWrapper
                                    .StudentMarksWrapperBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .createMarksWrapper()
                    ).ageCriteria(
                            new StudentAgeCriteria
                                    .StudentAgeCriteriaBuilder(22, 24)
                                    .createAgeCriteria()
                    ).courseCriteria(
                            new StudentCourseCriteria
                                    .StudentCourseCriteriaBuilder(3, 5)
                                    .createCourseCriteria()
                    ).createClassCriteria()
            );

            Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> classGroupOpCriteria = new HashMap<>();
            classGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE, new StudentMarkCriteria
                    .StudentMarkCriteriaBuilder(0, 6.4)
                    .createMarkCriteria()
            );

            classCriteria.put(L_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            new StudentMarksWrapper
                                    .StudentMarksWrapperBuilder()
                                    .groupOperationsCriteria(classGroupOpCriteria)
                                    .createMarksWrapper()
                    ).courseCriteria(
                            new StudentCourseCriteria
                                    .StudentCourseCriteriaBuilder(3, 5)
                                    .createCourseCriteria()
                    ).createClassCriteria()
            );

            classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.PHYSICAL_EDUCATION, new StudentMarkCriteria
                    .StudentMarkCriteriaBuilder(9, 10).createMarkCriteria()
            );

            classCriteria.put(B_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            new StudentMarksWrapper
                                    .StudentMarksWrapperBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .createMarksWrapper()
                    ).ageCriteria(
                            new StudentAgeCriteria
                                    .StudentAgeCriteriaBuilder(22, 24)
                                    .createAgeCriteria()
                    ).courseCriteria(
                            new StudentCourseCriteria
                                    .StudentCourseCriteriaBuilder(3, 5)
                                    .createCourseCriteria()
                    ).createClassCriteria()
            );

            classCriteria.put(O_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .ageCriteria(
                            new StudentAgeCriteria
                                    .StudentAgeCriteriaBuilder(18, 18)
                                    .createAgeCriteria()
                    ).courseCriteria(
                            new StudentCourseCriteria
                                    .StudentCourseCriteriaBuilder(1, 1)
                                    .createCourseCriteria()
                    ).createClassCriteria()
            );

            classGroupOpCriteria = new HashMap<>();
            classGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE, new StudentMarkCriteria
                    .StudentMarkCriteriaBuilder(7.5, 8.5).createMarkCriteria()
            );

            classCriteria.put(N_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            new StudentMarksWrapper.StudentMarksWrapperBuilder()
                                    .groupOperationsCriteria(classGroupOpCriteria)
                                    .createMarksWrapper()
                    ).ageCriteria(
                            new StudentAgeCriteria
                                    .StudentAgeCriteriaBuilder(22, 23)
                                    .createAgeCriteria()
                    ).courseCriteria(
                            new StudentCourseCriteria
                                    .StudentCourseCriteriaBuilder(3, 5)
                                    .createCourseCriteria()
                    ).createClassCriteria()
            );

            classMarksCriteria = new HashMap<>();
            classMarksCriteria.put(Subject.PHYSICAL_EDUCATION, new StudentMarkCriteria
                    .StudentMarkCriteriaBuilder(0, 6).createMarkCriteria()
            );

            classCriteria.put(I_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            new StudentMarksWrapper
                                    .StudentMarksWrapperBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .createMarksWrapper()
                    ).createClassCriteria()
            );

            classCriteria.put(RAND_CLASS, new StudentClassCriteria
                    .StudentClassCriteriaBuilder()
                    .createClassCriteria()
            );
        }

        return classCriteria;
    }
}
