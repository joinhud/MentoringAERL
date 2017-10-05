package com.epam.aerl.mentoring.aspect;

import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

@Aspect
public class LoggingUniversityServiceAspect {
  private static final Logger LOG = LogManager.getLogger(LoggingUniversityServiceAspect.class);

  @Pointcut("execution(* com.epam.aerl.mentoring.service.UniversityService.createNewUniversity(..))")
  public void createNewUniversityPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.UniversityService.findStudentsByUniversityId(..))")
  public void findStudentsByUniversityIdPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.UniversityService.findNotAssignedStudents(..))")
  public void findNotAssignedStudentsPointcut() {}

  @Pointcut("execution(* com.epam.aerl.mentoring.service.UniversityService.setUniversitiesStatus(..))")
  public void setUniversitiesStatusPointcut() {}

  @Before("createNewUniversityPointcut() && args(university)")
  public void beforeCreateNewUniversity(final UniversityDomainModel university) {
    LOG.info("Adding new university with name " + university.getName());
  }

  @Before("findStudentsByUniversityIdPointcut() && args(universityId)")
  public void beforeFindStudentsByUniversityId(final Long universityId) {
    LOG.info("Retrieving information about students of the university with ID: " + universityId);
  }

  @Before("findNotAssignedStudentsPointcut()")
  public void beforeFindNotAssignedStudents() {
    LOG.info("Retrieving information about students that are not assigned  to university or assigned to closed one or that is pending government approval.");
  }

  @Before("setUniversitiesStatusPointcut() && args(newStatus, universities)")
  public void beforeSetUniversitiesStatus(final UniversityStatus newStatus, final List<UniversityDomainModel> universities) {
    final StringBuilder builder = new StringBuilder("Setting status ");
    builder.append(newStatus.name()).append(" for universities with IDs: ");

    for (int i = 0; i < universities.size(); i++) {
      builder.append(universities.get(i).getId());

      if (i == universities.size() - 1) {
        builder.append('.');
      } else {
        builder.append(", ");
      }
    }

    LOG.info(builder.toString());
  }
}
