package com.epam.aerl.mentoring.dao;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.DaoLayerException;

public interface StudentDao {
  Student findStudentById(final Long id);
  Student create(final Student student) throws DaoLayerException;
  boolean deleteById(final Long id);
  Student update(final Student student) throws DaoLayerException;
}
