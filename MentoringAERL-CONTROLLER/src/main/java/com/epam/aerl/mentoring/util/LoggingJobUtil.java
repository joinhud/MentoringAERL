package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class LoggingJobUtil {
  private static final String WORKING_DIR = "C:\\Work\\ProjectsLogs\\MentoringAERL_Entity_logs";
  private static final String STUDENT_FILE_REGEXP = "^.+_student_\\d+\\x2Exml$";
  private static final Logger LOG = LogManager.getLogger(LoggingJobUtil.class);

  public Integer countFilesInDirectory() {
    Integer result = null;

    final File[] files = new File(WORKING_DIR).listFiles();

    if (files != null) {
      result = files.length;
    }

    return result;
  }

  public Integer loggedStudentsCount() {
    Integer result = null;

    final File[] files = new File(WORKING_DIR).listFiles((dir, name) -> name.matches(STUDENT_FILE_REGEXP));

    if (files != null) {
      result = files.length;
    }

    return result;
  }

  public Integer defineMaxStudentAge() {
    Integer result = null;

    final Set<Integer> ages = new HashSet<>();

    final File[] files = new File(WORKING_DIR).listFiles((dir, name) -> name.matches(STUDENT_FILE_REGEXP));

    if (files != null) {
      try {
        final JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        for (File file : files) {
          final Student student = (Student) jaxbUnmarshaller.unmarshal(file);
          ages.add(student.getAge());
        }

        result = Collections.max(ages);
      } catch (JAXBException e) {
        LOG.error(e);
      }
    }

    return result;
  }
}
