package com.epam.aerl.mentoring.dao;

import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.UniversityStatus;

import java.util.List;

public interface UniversityDao {
  UniversityDTO create(final UniversityDTO universityDTO) throws DaoLayerException;
  UniversityDTO findById(final Long id);
  UniversityDTO update(final UniversityDTO universityDTO) throws DaoLayerException;
  List<UniversityDTO> findUniversitiesByStatus(final UniversityStatus status);
}
