package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class EntityLogger<T> {
  protected static final String FILE_NAME_SEPARATOR = "_";
  protected static final String EXTENSION = ".xml";
  protected static final String PATH = "C:\\Work\\ProjectsLogs\\MentoringAERL_Entity_logs\\";

  @Autowired
  @Qualifier("currentDate")
  protected LocalDate currentDate;

  @Autowired
  @Qualifier("dateFormatter")
  protected DateTimeFormatter formatter;

  public abstract boolean logEntity(final T entity) throws ServiceLayerException;
}
