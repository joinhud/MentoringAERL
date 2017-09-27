package com.epam.aerl.mentoring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service("studentParametersGenerator")
public class StudentParametersGenerator {
  private static final int FIRST_COURSE = 1;
  private static final int SECOND_COURSE = 2;
  private static final int THIRD_COURSE = 3;
  private static final int FOURTH_COURSE = 4;
  private static final int FIFTH_COURSE = 5;
  private static final Random RANDOM = new Random();

  @Autowired
  @Qualifier("studentPropertiesHolder")
  private StudentPropertiesHolder holder;

  @Autowired
  @Qualifier("randomStudentNameGenerator")
  private RandomStudentNameGenerator randomStudentNameGenerator;

  public void setHolder(StudentPropertiesHolder holder) {
    this.holder = holder;
  }

  public void setRandomStudentNameGenerator(RandomStudentNameGenerator randomStudentNameGenerator) {
    this.randomStudentNameGenerator = randomStudentNameGenerator;
  }

  private int randomIntRange(final int min, final int max) {
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
          generationMinAge = (int) holder.getStudentCourseAgeRanges().get(FIRST_COURSE).getMin();
          generationMaxAge = (int) holder.getStudentCourseAgeRanges().get(FIRST_COURSE).getMax();
          break;
        case SECOND_COURSE:
          generationMinAge = (int) holder.getStudentCourseAgeRanges().get(SECOND_COURSE).getMin();
          generationMaxAge = (int) holder.getStudentCourseAgeRanges().get(SECOND_COURSE).getMax();
          break;
        case THIRD_COURSE:
          generationMinAge = (int) holder.getStudentCourseAgeRanges().get(THIRD_COURSE).getMin();
          generationMaxAge = (int) holder.getStudentCourseAgeRanges().get(THIRD_COURSE).getMax();
          break;
        case FOURTH_COURSE:
          generationMinAge = (int) holder.getStudentCourseAgeRanges().get(FOURTH_COURSE).getMin();
          generationMaxAge = (int) holder.getStudentCourseAgeRanges().get(FOURTH_COURSE).getMax();
          break;
        case FIFTH_COURSE:
          generationMinAge = (int) holder.getStudentCourseAgeRanges().get(FIFTH_COURSE).getMin();
          generationMaxAge = (int) holder.getStudentCourseAgeRanges().get(FIFTH_COURSE).getMax();
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
        minAge = (int) holder.getStudentCourseAgeRanges().get(FIRST_COURSE).getMin();
        maxAge = (int) holder.getStudentCourseAgeRanges().get(FIRST_COURSE).getMax();
        break;
      case SECOND_COURSE:
        minAge = (int) holder.getStudentCourseAgeRanges().get(SECOND_COURSE).getMin();
        maxAge = (int) holder.getStudentCourseAgeRanges().get(SECOND_COURSE).getMax();
        break;
      case THIRD_COURSE:
        minAge = (int) holder.getStudentCourseAgeRanges().get(THIRD_COURSE).getMin();
        maxAge = (int) holder.getStudentCourseAgeRanges().get(THIRD_COURSE).getMax();
        break;
      case FOURTH_COURSE:
        minAge = (int) holder.getStudentCourseAgeRanges().get(FOURTH_COURSE).getMin();
        maxAge = (int) holder.getStudentCourseAgeRanges().get(FOURTH_COURSE).getMax();
        break;
      case FIFTH_COURSE:
        minAge = (int) holder.getStudentCourseAgeRanges().get(FIFTH_COURSE).getMin();
        maxAge = (int) holder.getStudentCourseAgeRanges().get(FIFTH_COURSE).getMax();
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
    int minMark = (int) holder.getStudentMarkRange().getMin();
    int maxMark = (int) holder.getStudentMarkRange().getMax();

    return randomIntRange(minMark, maxMark);
  }

  public String generateStudentName() {
    return randomStudentNameGenerator.generateName();
  }

  public String generateStudentSurname() {
    return randomStudentNameGenerator.generateSurname();
  }

  private boolean checkRange(final int min, final int max) {
    return min <= max;
  }

  private boolean checkCourse(final int course) {
    return FIFTH_COURSE >= course && FIRST_COURSE <= course;
  }

  private boolean checkAge(final int age) {
    int minAge = (int) holder.getStudentAgeRange().getMin();
    int maxAge = (int) holder.getStudentAgeRange().getMax();

    return maxAge >= age && minAge <= age;
  }

  private boolean checkMark(final int mark) {
    int minMark = (int) holder.getStudentMarkRange().getMin();
    int maxMark = (int) holder.getStudentMarkRange().getMax();

    return maxMark >= mark && minMark <= mark;
  }
}
