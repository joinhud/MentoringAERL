package com.epam.aerl.mentoring.aspect;

import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

@Aspect
public class LoggingStudentServiceAspect {
  private static final Logger LOG = LogManager.getLogger(LoggingStudentServiceAspect.class);

  @Pointcut("execution(* com.epam.aerl.mentoring.service.StudentService.generateStudentsByCriteria(..))")
  public void generateStudentsByCriteriaPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.StudentService.generateCertainAmountOfStudents(..))")
  public void generateCertainAmountOfStudentsPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.StudentService.findStudentsByIds(..))")
  public void findStudentsByIdsPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.StudentService.removeStudentsByIds(..))")
  public void removeStudentsByIdsPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.StudentService.assignStudentsToUniversity(..))")
  public void assignStudentsToUniversityPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.StudentService.updateStudentsInformation(..))")
  public void updateStudentsInformationPointcut() {}

  @Before("generateStudentsByCriteriaPointcut() && args(criteria)")
  public void beforeGenerateStudentsByCriteria(final String criteria) {
    LOG.info("Generating students by criteria : " + criteria + ".");
  }

  @Before("generateCertainAmountOfStudentsPointcut() && args(amount)")
  public void beforeGenerateCertainAmountOfStudents(final int amount) {
    LOG.info("Generating " + amount + " random students.");
  }

  @Before("findStudentsByIdsPointcut() && args(ids)")
  public void beforeFindStudentsByIds(final List<Long> ids) {
    final StringBuilder builder = new StringBuilder("Retrieving information about students with IDs: ");
    for (int i = 0; i < ids.size(); i++) {
      builder.append(ids.get(i));

      if (i == ids.size() - 1) {
        builder.append('.');
      } else {
        builder.append(", ");
      }
    }

    LOG.info(builder.toString());
  }

  @Before("removeStudentsByIdsPointcut() && args(ids)")
  public void beforeRemoveStudentsByIds(final List<Long> ids) {
    final StringBuilder builder = new StringBuilder("Remove students with IDs: ");
    for (int i = 0; i < ids.size(); i++) {
      builder.append(ids.get(i));

      if (i == ids.size() - 1) {
        builder.append('.');
      } else {
        builder.append(", ");
      }
    }

    LOG.info(builder.toString());
  }

  @Before("assignStudentsToUniversityPointcut() && args(university, students)")
  public void beforeAssignStudentsToUniversity(final UniversityDomainModel university, final List<StudentDomainModel> students) {
    final StringBuilder builder = new StringBuilder("Assigning to the university with ID ");
    builder.append(university.getId()).append(" students with IDs: ");

    for (int i = 0; i < students.size(); i++) {
      builder.append(students.get(i).getId());

      if (i == students.size() - 1) {
        builder.append('.');
      } else {
        builder.append(", ");
      }
    }

    LOG.info(builder.toString());
  }

  @Before("updateStudentsInformationPointcut() && args(students)")
  public void beforeUpdateStudentsInformation(final List<StudentDomainModel> students) {
    final StringBuilder builder = new StringBuilder("Updating information about students with IDs: ");

    for (int i = 0; i < students.size(); i++) {
      builder.append(students.get(i).getId());

      if (i == students.size() - 1) {
        builder.append('.');
      } else {
        builder.append(", ");
      }
    }

    LOG.info(builder.toString());
  }
}
