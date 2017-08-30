package com.epam.aerl.mentoring.util;

import java.util.Random;

public class StudentParametersGenerator {
    private static final int FIRST_COURSE_MIN_AGE = 18;
    private static final int FIRST_COURSE_MAX_AGE = 20;
    private static final int SECOND_COURSE_MIN_AGE = 18;
    private static final int SECOND_COURSE_MAX_AGE = 21;
    private static final int THIRD_COURSE_MIN_AGE = 19;
    private static final int THIRD_COURSE_MAX_AGE = 22;
    private static final int FOURTH_COURSE_MIN_AGE = 20;
    private static final int FOURTH_COURSE_MAX_AGE = 24;
    private static final int FIFTH_COURSE_MIN_AGE = 21;
    private static final int FIFTH_COURSE_MAX_AGE = 24;

    private static final int FIRST_COURSE = 1;
    private static final int SECOND_COURSE = 2;
    private static final int THIRD_COURSE = 3;
    private static final int FOURTH_COURSE = 4;
    private static final int FIFTH_COURSE = 5;

    private static final int MIN_STUDENT_AGE = 18;
    private static final int MAX_STUDENT_AGE = 24;

    private static final int MIN_STUDENT_MARK = 0;
    private static final int MAX_STUDENT_MARK = 10;

    private static final Random RANDOM = new Random();

    private int randomIntRange(final int min,final int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public Integer generateStudentCourse(final int min, final int max) {
        Integer result = null;

        if (checkRange(min, max) && checkCourse(min) && checkCourse(max)) {
            result = randomIntRange(min, max);
        }

        return result;
    }

    public Integer generateRandomStudentCourse() {
        return randomIntRange(FIRST_COURSE, FIFTH_COURSE);
    }

    public Integer generateStudentAgeByCourseAndRange(final int course, final int minAge, final int maxAge) {
        Integer result = null;

        if (checkRange(minAge, maxAge) && checkCourse(course) && checkAge(minAge) && checkAge(maxAge)) {
            int generationMinAge;
            int generationMaxAge;

            switch (course) {
                case FIRST_COURSE:
                    generationMinAge = FIRST_COURSE_MIN_AGE;
                    generationMaxAge = FIRST_COURSE_MAX_AGE;
                    break;
                case SECOND_COURSE:
                    generationMinAge = SECOND_COURSE_MIN_AGE;
                    generationMaxAge = SECOND_COURSE_MAX_AGE;
                    break;
                case THIRD_COURSE:
                    generationMinAge = THIRD_COURSE_MIN_AGE;
                    generationMaxAge = THIRD_COURSE_MAX_AGE;
                    break;
                case FOURTH_COURSE:
                    generationMinAge = FOURTH_COURSE_MIN_AGE;
                    generationMaxAge = FOURTH_COURSE_MAX_AGE;
                    break;
                case FIFTH_COURSE:
                    generationMinAge = FIFTH_COURSE_MIN_AGE;
                    generationMaxAge = FIFTH_COURSE_MAX_AGE;
                    break;
                default:
                    generationMinAge = 0;
                    generationMaxAge = 0;
                    break;
            }

            if (generationMinAge != 0) {
                if (minAge > generationMinAge) {
                    generationMinAge = minAge;
                }
                if (maxAge < generationMaxAge) {
                    generationMaxAge = maxAge;
                }

                result = randomIntRange(generationMinAge, generationMaxAge);
            }
        }

        return result;
    }

    public Integer generateStudentAgeByCourse(final int course) {
        Integer age = null;
        int minAge;
        int maxAge;

        switch (course) {
            case FIRST_COURSE:
                minAge = FIRST_COURSE_MIN_AGE;
                maxAge = FIRST_COURSE_MAX_AGE;
                break;
            case SECOND_COURSE:
                minAge = SECOND_COURSE_MIN_AGE;
                maxAge = SECOND_COURSE_MAX_AGE;
                break;
            case THIRD_COURSE:
                minAge = THIRD_COURSE_MIN_AGE;
                maxAge = THIRD_COURSE_MAX_AGE;
                break;
            case FOURTH_COURSE:
                minAge = FOURTH_COURSE_MIN_AGE;
                maxAge = FOURTH_COURSE_MAX_AGE;
                break;
            case FIFTH_COURSE:
                minAge = FIFTH_COURSE_MIN_AGE;
                maxAge = FIFTH_COURSE_MAX_AGE;
                break;
            default:
                minAge = 0;
                maxAge = 0;
                break;
        }

        if (minAge != maxAge) {
            age = randomIntRange(minAge, maxAge);
        }

        return age;
    }

    public Integer generateStudentMark(final int minMark, final int maxMark) {
        Integer result = null;

        if (checkRange(minMark, maxMark) && checkMark(minMark) && checkMark(maxMark)) {
            result = randomIntRange(minMark, maxMark);
        }

        return result;
    }

    public Integer generateRandomStudentMark() {
        return randomIntRange(MIN_STUDENT_MARK, MAX_STUDENT_MARK);
    }

    private boolean checkRange(final int min, final int max) {
        return min <= max;
    }

    private boolean checkCourse(final int course) {
        return FIFTH_COURSE >= course  && FIRST_COURSE <= course;
    }

    private boolean checkAge(final int age) {
        return MAX_STUDENT_AGE >= age && MIN_STUDENT_AGE <= age;
    }

    private boolean checkMark(final int mark) {
        return MAX_STUDENT_MARK >= mark && MIN_STUDENT_MARK <= mark;
    }
}
