package com.epam.aerl.mentoring.dao;

import com.epam.aerl.mentoring.entity.University;
import com.epam.aerl.mentoring.exception.DaoLayerException;

public interface UniversityDao {
  University create(final University university) throws DaoLayerException;
  University findById(final Long id);
  University update(final University university) throws DaoLayerException;
}
