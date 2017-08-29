package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentMarksWrapper;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.Subject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.epam.aerl.mentoring.entity.StudentRangeCriteria.newBuilder;

public class StudentClassCriteriaHolder {
    private static volatile StudentClassCriteriaHolder instance;

    private Map<String, StudentClassCriteria> classCriteria;
    private Map<String, StudentClassCriteria> cachedClassCriteria = new HashMap<>();

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

    public Map<String, StudentClassCriteria> getStudentClassCriteria() {
        if (classCriteria == null) {
            classCriteria = new HashMap<>();

            Map<Subject, StudentRangeCriteria> classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.MATH,
                    newBuilder().min(9).max(10).build()
            );

            classCriteria.put(GenerationClass.M.toString(),
                    StudentClassCriteria
                            .newBuilder()
                            .studentMarksWrapperCriteria(
                                    StudentMarksWrapper
                                            .newBuilder()
                                            .marksCriteria(classMarksCriteria)
                                            .build()
                            ).build()
            );

            classCriteria.put(GenerationClass.Y.toString(),
                    StudentClassCriteria
                            .newBuilder()
                            .ageCriteria(
                                    newBuilder()
                                            .min(18)
                                            .max(20)
                                            .build()
                            ).courseCriteria(
                                    newBuilder()
                                            .min(1)
                                            .max(4)
                                            .build()
                            ).build()
            );

            classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.MATH,
                    newBuilder()
                            .min(10)
                            .max(10)
                            .build()
            );
            classMarksCriteria.put(Subject.PHILOSOPHY,
                    newBuilder()
                            .min(10)
                            .max(10)
                            .build()
            );

            classCriteria.put(GenerationClass.P.toString(),
                    StudentClassCriteria
                            .newBuilder()
                            .studentMarksWrapperCriteria(
                                    StudentMarksWrapper
                                            .newBuilder()
                                            .marksCriteria(classMarksCriteria)
                                            .build()
                            ).ageCriteria(
                                    newBuilder()
                                            .min(22)
                                            .max(24)
                                            .build()
                            ).courseCriteria(
                                    newBuilder()
                                            .min(3)
                                            .max(5)
                                            .build()
                            ).build()
            );

            Map<StudentMarksWrapper.GroupOperation, StudentRangeCriteria> classGroupOpCriteria = new HashMap<>();
            classGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE,
                    newBuilder()
                            .min(1)
                            .max(6.4)
                            .build()
            );

            classCriteria.put(GenerationClass.L.toString(),
                    StudentClassCriteria
                            .newBuilder()
                            .studentMarksWrapperCriteria(
                                    StudentMarksWrapper
                                            .newBuilder()
                                            .groupOperationsCriteria(classGroupOpCriteria)
                                            .build()
                            ).courseCriteria(
                                    newBuilder()
                                            .min(3)
                                            .max(5)
                                            .build()
                            ).build()
            );

            classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.PHYSICAL_EDUCATION,
                    newBuilder()
                            .min(9)
                            .max(10)
                            .build()
            );

            classCriteria.put(GenerationClass.B.toString(), StudentClassCriteria
                    .newBuilder()
                    .studentMarksWrapperCriteria(
                            StudentMarksWrapper
                                    .newBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .build()
                    ).ageCriteria(
                            newBuilder()
                                    .min(22)
                                    .max(24)
                                    .build()
                    ).courseCriteria(
                            newBuilder()
                                    .min(3)
                                    .max(5)
                                    .build()
                    ).build()
            );

            classCriteria.put(GenerationClass.O.toString(), StudentClassCriteria
                    .newBuilder()
                    .ageCriteria(
                            newBuilder()
                                    .min(18)
                                    .max(18)
                                    .build()
                    ).courseCriteria(
                            newBuilder()
                                    .min(1)
                                    .max(1)
                                    .build()
                    ).build()
            );

            classGroupOpCriteria = new HashMap<>();
            classGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE,
                    newBuilder().min(7.5).max(8.5).build()
            );

            classCriteria.put(GenerationClass.N.toString(), StudentClassCriteria
                    .newBuilder()
                    .studentMarksWrapperCriteria(
                            StudentMarksWrapper
                                    .newBuilder()
                                    .groupOperationsCriteria(classGroupOpCriteria)
                                    .build()
                    ).ageCriteria(
                            newBuilder()
                                    .min(22)
                                    .max(23)
                                    .build()
                    ).courseCriteria(
                            newBuilder()
                                    .min(3)
                                    .max(5)
                                    .build()
                    ).build()
            );

            classMarksCriteria = new HashMap<>();
            classMarksCriteria.put(Subject.PHYSICAL_EDUCATION,
                    newBuilder().min(0).max(6).build()
            );

            classCriteria.put(GenerationClass.I.toString(), StudentClassCriteria
                    .newBuilder()
                    .studentMarksWrapperCriteria(
                            StudentMarksWrapper
                                    .newBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .build()
                    ).build()
            );

            classCriteria.put(GenerationClass.RAND.toString().toLowerCase(), StudentClassCriteria
                    .newBuilder()
                    .build()
            );
        }

        return classCriteria;
    }

    public boolean putCombinedStudentClassCriteria(String combinedClassName, StudentClassCriteria criteria) {
        boolean result = false;

        if (combinedClassName != null && criteria != null) {
            cachedClassCriteria.put(combinedClassName, criteria);
            result = true;
        }

        return result;
    }

    public StudentClassCriteria getCriteriaByGenerationClass(String className) {
        StudentClassCriteria result = null;

        if (className != null) {
            if (getStudentClassCriteria().containsKey(className)) {
                result = classCriteria.get(className);
            } else if (cachedClassCriteria.containsKey(className)) {
                result = cachedClassCriteria.get(className);
            }
        }

        return result;
    }
}
