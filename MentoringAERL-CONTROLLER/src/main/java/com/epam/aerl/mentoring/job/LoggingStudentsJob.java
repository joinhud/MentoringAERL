package com.epam.aerl.mentoring.job;

import com.epam.aerl.mentoring.util.LoggingJobUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class LoggingStudentsJob extends QuartzJobBean {
  private static final Logger LOG = LogManager.getLogger(LoggingStudentsJob.class);
  private static final String FILES_COUNT = "Count of logged xml files: ";
  private static final String LOGGED_STUDENTS_COUNT = "Logged students count: ";
  private static final String STUDENT_MAX_AGE = "Max age of logged students is ";

  private LoggingJobUtil loggingJobUtil;

  public void setLoggingJobUtil(LoggingJobUtil loggingJobUtil) {
    this.loggingJobUtil = loggingJobUtil;
  }

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    LOG.info(FILES_COUNT + loggingJobUtil.countFilesInDirectory());
    LOG.info(LOGGED_STUDENTS_COUNT + loggingJobUtil.loggedStudentsCount());
    LOG.info(STUDENT_MAX_AGE + loggingJobUtil.defineMaxStudentAge());
  }
}
