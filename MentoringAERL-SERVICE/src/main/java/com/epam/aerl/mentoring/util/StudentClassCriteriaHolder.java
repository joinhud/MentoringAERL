package com.epam.aerl.mentoring.util;

import static com.epam.aerl.mentoring.entity.StudentClassCriteria.createClassCriteriaBuilder;
import static com.epam.aerl.mentoring.entity.StudentMarksWrapper.createMarksWrapperBuilder;
import static com.epam.aerl.mentoring.entity.StudentRangeCriteria.createRangeCriteriaBuilder;

import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentMarksWrapper;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.Subject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service("studentClassCriteriaHolder")
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
                    createRangeCriteriaBuilder().min(9).max(10).build()
            );

            classCriteria.put(GenerationClass.M.toString(),
                    createClassCriteriaBuilder()
                            .studentMarksWrapperCriteria(
                                    createMarksWrapperBuilder()
                                            .marksCriteria(classMarksCriteria)
                                            .build()
                            ).build()
            );

            classCriteria.put(GenerationClass.Y.toString(),
                    createClassCriteriaBuilder()
                            .ageCriteria(
                                    createRangeCriteriaBuilder()
                                            .min(18)
                                            .max(20)
                                            .build()
                            ).courseCriteria(
                                    createRangeCriteriaBuilder()
                                            .min(1)
                                            .max(4)
                                            .build()
                            ).build()
            );

            classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.MATH,
                    createRangeCriteriaBuilder()
                            .min(10)
                            .max(10)
                            .build()
            );
            classMarksCriteria.put(Subject.PHILOSOPHY,
                    createRangeCriteriaBuilder()
                            .min(10)
                            .max(10)
                            .build()
            );

            classCriteria.put(GenerationClass.P.toString(),
                    createClassCriteriaBuilder()
                            .studentMarksWrapperCriteria(
                                    createMarksWrapperBuilder()
                                            .marksCriteria(classMarksCriteria)
                                            .build()
                            ).ageCriteria(
                                    createRangeCriteriaBuilder()
                                            .min(22)
                                            .max(24)
                                            .build()
                            ).courseCriteria(
                                    createRangeCriteriaBuilder()
                                            .min(3)
                                            .max(5)
                                            .build()
                            ).build()
            );

            Map<StudentMarksWrapper.GroupOperation, StudentRangeCriteria> classGroupOpCriteria = new HashMap<>();
            classGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE,
                    createRangeCriteriaBuilder()
                            .min(1)
                            .max(6.4)
                            .build()
            );

            classCriteria.put(GenerationClass.L.toString(),
                    createClassCriteriaBuilder()
                            .studentMarksWrapperCriteria(
                                    createMarksWrapperBuilder()
                                            .groupOperationsCriteria(classGroupOpCriteria)
                                            .build()
                            ).courseCriteria(
                                    createRangeCriteriaBuilder()
                                            .min(3)
                                            .max(5)
                                            .build()
                            ).build()
            );

            classMarksCriteria = new LinkedHashMap<>();
            classMarksCriteria.put(Subject.PHYSICAL_EDUCATION,
                    createRangeCriteriaBuilder()
                            .min(9)
                            .max(10)
                            .build()
            );

            classCriteria.put(GenerationClass.B.toString(),
                    createClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            createMarksWrapperBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .build()
                    ).ageCriteria(
                            createRangeCriteriaBuilder()
                                    .min(22)
                                    .max(24)
                                    .build()
                    ).courseCriteria(
                            createRangeCriteriaBuilder()
                                    .min(3)
                                    .max(5)
                                    .build()
                    ).build()
            );

            classCriteria.put(GenerationClass.O.toString(),
                    createClassCriteriaBuilder()
                    .ageCriteria(
                            createRangeCriteriaBuilder()
                                    .min(18)
                                    .max(18)
                                    .build()
                    ).courseCriteria(
                            createRangeCriteriaBuilder()
                                    .min(1)
                                    .max(1)
                                    .build()
                    ).build()
            );

            classGroupOpCriteria = new HashMap<>();
            classGroupOpCriteria.put(StudentMarksWrapper.GroupOperation.AVERAGE,
                    createRangeCriteriaBuilder().min(7.5).max(8.5).build()
            );

            classCriteria.put(GenerationClass.N.toString(),
                    createClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            createMarksWrapperBuilder()
                                    .groupOperationsCriteria(classGroupOpCriteria)
                                    .build()
                    ).ageCriteria(
                            createRangeCriteriaBuilder()
                                    .min(22)
                                    .max(23)
                                    .build()
                    ).courseCriteria(
                            createRangeCriteriaBuilder()
                                    .min(3)
                                    .max(5)
                                    .build()
                    ).build()
            );

            classMarksCriteria = new HashMap<>();
            classMarksCriteria.put(Subject.PHYSICAL_EDUCATION,
                    createRangeCriteriaBuilder().min(0).max(6).build()
            );

            classCriteria.put(GenerationClass.I.toString(),
                    createClassCriteriaBuilder()
                    .studentMarksWrapperCriteria(
                            createMarksWrapperBuilder()
                                    .marksCriteria(classMarksCriteria)
                                    .build()
                    ).build()
            );

            classCriteria.put(GenerationClass.RAND.toString().toLowerCase(),
                    createClassCriteriaBuilder()
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
