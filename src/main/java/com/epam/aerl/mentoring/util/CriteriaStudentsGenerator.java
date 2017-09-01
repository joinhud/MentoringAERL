package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentMarksWrapper.GroupOperation;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.Subject;

import java.util.*;

public class CriteriaStudentsGenerator {
    private static final int MAX_MARK = 10;
    private static final int MIN_MARK = 0;

    private StudentParametersGenerator generator;
    private StudentClassCriteriaHolder holder;

    public void setGenerator(StudentParametersGenerator generator) {
        this.generator = generator;
    }

    public void setHolder(StudentClassCriteriaHolder holder) {
        this.holder = holder;
    }

    public List<Student> generateStudents(final Map<String, Integer> criteria) {

        List<Student> students = null;

        if (criteria != null) {
            students = new ArrayList<>();

            for (Map.Entry<String, Integer> parameter : criteria.entrySet()) {
                if (parameter.getKey() != null && !parameter.getKey().equals(GenerationClass.S.toString())) {
                    Integer count = parameter.getValue();

                    if (count != null) {
                        for (int i = 0; i < count; i++) {
                            Student student = generate(holder.getStudentClassCriteria().get(parameter.getKey()));
                            students.add(student);
                        }
                    }
                }
            }
        }

        return students;
    }

    public Student generate(final StudentClassCriteria criteria) {
        Student student = null;

        if (criteria != null) {
            student = new Student();
            final Map<Subject, Integer> marks = new HashMap<>();

            generateByCourseCriteria(criteria, student);
            generateByAgeCriteria(criteria, student);
            generateByMarksWrapperCriteria(criteria, marks);

            student.setMarks(marks);
        }

        return student;
    }

    private void generateByCourseCriteria(final StudentClassCriteria criteria, final Student student) {
        if (criteria != null && student != null) {
            if (criteria.getCourseCriteria() != null) {
                int minCourse = (int) criteria.getCourseCriteria().getMin();
                int maxCourse = (int) criteria.getCourseCriteria().getMax();

                student.setCourse(generator.generateStudentCourse(minCourse, maxCourse));
            } else {
                student.setCourse(generator.generateRandomStudentCourse());
            }
        }
    }

    private void generateByAgeCriteria(final StudentClassCriteria criteria, final Student student) {
        if (criteria != null && student != null) {
            if (criteria.getAgeCriteria() != null) {
                int minAge = (int) criteria.getAgeCriteria().getMin();
                int maxAge = (int) criteria.getAgeCriteria().getMax();

                student.setAge(generator.generateStudentAgeByCourseAndRange(student.getCourse(), minAge, maxAge));
            } else {
                student.setAge(generator.generateStudentAgeByCourse(student.getCourse()));
            }
        }
    }

    private int[] generateMarksFromAverageAlgorithm(double min, double max, final int count) {
        int[] result = null;

        if (min <= count * MAX_MARK && max >= MIN_MARK && count > 0) {
            result = new int[count];

            for (int i = 0; i < count; i++) {
                if (i == count - 1) {
                    result[i] = generator.generateStudentMark((int) min, (int) max);
                } else if (min < MAX_MARK) {
                    if (max > MAX_MARK) {
                        max -= MAX_MARK;
                    }
                    result[i] = MIN_MARK;
                } else {
                    result[i] = MAX_MARK;
                    min -= MAX_MARK;
                    max -= MAX_MARK;
                }
            }
        }

        return result;
    }

    private void generateByGroupOperationsCriteria(final StudentClassCriteria criteria, final Map<Subject, Integer> marks) {
        if (criteria != null && marks != null) {
            if (criteria.getStudentMarksWrapperCriteria().getGroupOperationsCriteria() != null) {
                Map<GroupOperation, StudentRangeCriteria> groupOpCriteria = criteria
                        .getStudentMarksWrapperCriteria()
                        .getGroupOperationsCriteria();

                if (groupOpCriteria.containsKey(GroupOperation.AVERAGE)) {
                    int count = Subject.values().length;
                    final List<Subject> generatedSubjects = Arrays.asList(Subject.values());
                    double minAvgRangeValue = groupOpCriteria
                            .get(GroupOperation.AVERAGE)
                            .getMin() * count;
                    double maxAvgRangeValue = groupOpCriteria
                            .get(GroupOperation.AVERAGE)
                            .getMax() * count;

                    if (criteria.getStudentMarksWrapperCriteria().getMarksCriteria() != null) {
                        Map<Subject, StudentRangeCriteria> marksCriteria = criteria
                                .getStudentMarksWrapperCriteria()
                                .getMarksCriteria();

                        for (Map.Entry<Subject, StudentRangeCriteria> marksCriteriaElement : marksCriteria.entrySet()) {
                            maxAvgRangeValue -= marksCriteriaElement.getValue().getMin();
                            minAvgRangeValue -= marksCriteriaElement.getValue().getMax();
                            generatedSubjects.remove(marksCriteriaElement.getKey());
                            count--;
                        }
                    }

                    final int[] generatedMarks = generateMarksFromAverageAlgorithm(minAvgRangeValue, maxAvgRangeValue, count);

                    for (int i = 0; i < count; i++) {
                        marks.put(generatedSubjects.get(i), generatedMarks[i]);
                    }
                }
            }
        }
    }

    private void generateByMarksCriteria(final StudentClassCriteria criteria, final Map<Subject, Integer> marks) {
        if (criteria != null && marks != null) {
            if (criteria.getStudentMarksWrapperCriteria().getMarksCriteria() != null) {
                Map<Subject, StudentRangeCriteria> marksCriteria = criteria
                        .getStudentMarksWrapperCriteria()
                        .getMarksCriteria();

                for (Subject subject : Subject.values()) {
                    if (marksCriteria.containsKey(subject)) {
                        int minMark = (int) marksCriteria.get(subject).getMin();
                        int maxMark = (int) marksCriteria.get(subject).getMax();

                        marks.put(subject, generator.generateStudentMark(minMark, maxMark));
                    } else {
                        marks.put(subject, generator.generateRandomStudentMark());
                    }
                }
            }
        }
    }

    private void generateByMarksWrapperCriteria(final StudentClassCriteria criteria, final Map<Subject, Integer> marks) {
        if (criteria != null && marks != null) {
            if (criteria.getStudentMarksWrapperCriteria() != null) {
                generateByGroupOperationsCriteria(criteria, marks);
                generateByMarksCriteria(criteria, marks);
            } else {
                for (Subject subject : Subject.values()) {
                    marks.put(subject, generator.generateRandomStudentMark());
                }
            }
        }
    }
}
