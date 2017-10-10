package com.epam.aerl.mentoring.dao;

import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;

import java.util.List;

public interface StudentDao {
  StudentDTO findStudentById(final Long id);
  StudentDTO create(final StudentDTO studentDTO) throws DaoLayerException;
  boolean deleteById(final Long id);
  StudentDTO update(final StudentDTO studentDTO) throws DaoLayerException;
  List<StudentDTO> findNotAssignedStudents();
  List<StudentDTO> findStudentsByIds(final List<Long> ids);
}
