package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentMarkCriteria;
import com.epam.aerl.mentoring.type.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaStudentsGenerator {
    private StudentParametersGenerator generator = new StudentParametersGenerator();
    private StudentClassCriteriaHolder holder = StudentClassCriteriaHolder.getInstance();

    public List<Student> generateStudents(final Map<String, Integer> criteria) {

        List<Student> students = null;

        if (criteria != null) {
            students = new ArrayList<>();

            for (Map.Entry<String, Integer> parameter : criteria.entrySet()) {
                for (int i = 0; i < parameter.getValue(); i++) {
                    Student student = generate(holder.receiveStudentClassCriteria().get(parameter.getKey()));
                    students.add(student);
                }
            }
        }

        return students;
    }

    public Student generate(final StudentClassCriteria criteria) {
        Student student = null;

        if (criteria != null) {
            student = new Student();

            if (criteria.getCourseCriteria() != null) {
                int minCourse = criteria.getCourseCriteria().getMinCourse();
                int maxCourse = criteria.getCourseCriteria().getMaxCourse();

                student.setCourse(generator.generateStudentCourse(minCourse, maxCourse));
            } else {
                student.setCourse(generator.generateRandomStudentCourse());
            }

            if (criteria.getAgeCriteria() != null) {
                int minAge = criteria.getAgeCriteria().getMinAge();
                int maxAge = criteria.getAgeCriteria().getMaxAge();

                student.setAge(generator.generateStudentAgeByCourseAndRange(student.getCourse(), minAge, maxAge));
            } else {
                student.setAge(generator.generateStudentAgeByCourse(student.getCourse()));
            }


            final Map<Subject, Integer> marks = new HashMap<>();

            if (criteria.getStudentMarksWrapperCriteria() != null) {
                if (criteria.getStudentMarksWrapperCriteria().getMarksCriteria() != null) {
                    Map<Subject, StudentMarkCriteria> marksCriteria = criteria.getStudentMarksWrapperCriteria().getMarksCriteria();

                    for (Subject subject : Subject.values()) {
                        if (marksCriteria.containsKey(subject)) {
                            int minMark = (int) marksCriteria.get(subject).getMinMark();
                            int maxMark = (int) marksCriteria.get(subject).getMaxMark();

                            marks.put(subject, generator.generateStudentMark(minMark, maxMark));
                        } else {
                            marks.put(subject, generator.generateRandomStudentMark());
                        }
                    }
                }
            } else {
                for (Subject subject : Subject.values()) {
                    marks.put(subject, generator.generateRandomStudentMark());
                }
            }

            student.setMarks(marks);
        }

        return student;
    }
}
