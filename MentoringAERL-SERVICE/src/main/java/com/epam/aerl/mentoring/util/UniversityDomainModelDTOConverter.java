package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.entity.UniversityStatusDTO;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service("universityDomainModelDTOConverter")
public class UniversityDomainModelDTOConverter implements DomainModelDTOConverter<UniversityDomainModel, UniversityDTO> {

  @Autowired
  @Qualifier("studentDomainModelDTOConverter")
  private StudentDomainModelDTOConverter studentDomainModelDTOConverter;

  public void setStudentDomainModelDTOConverter(
      StudentDomainModelDTOConverter studentDomainModelDTOConverter) {
    this.studentDomainModelDTOConverter = studentDomainModelDTOConverter;
  }

  @Override
  public UniversityDTO convertModelToDTO(UniversityDomainModel model) {
    UniversityDTO result = null;

    if (model != null) {
      result = new UniversityDTO();
      result.setId(model.getId());
      result.setName(model.getName());
      result.setDescription(model.getDescription());
      result.setFoundationDate(model.getFoundationDate());

      final UniversityStatusDTO universityStatusDTO = new UniversityStatusDTO();
      universityStatusDTO.setId((long) (model.getStatus().ordinal() + 1));
      universityStatusDTO.setStatusName(model.getStatus());

      result.setUniversityStatusDTO(universityStatusDTO);

      if (model.getStudents() != null) {
        final Set<StudentDTO> studentDTOs = new HashSet<>();

        for (StudentDomainModel studentModel : model.getStudents()) {
          StudentDTO studentDTO = studentDomainModelDTOConverter.convertModelToDTO(studentModel);
          studentDTOs.add(studentDTO);
        }

        result.setStudentDTOs(studentDTOs);
      }

      result.setCreationInDB(model.getCreationInDbDate());
      result.setLastUpdateInDB(model.getLastUpdateInDbDate());
    }

    return result;
  }

  @Override
  public UniversityDomainModel convertDTOToModel(UniversityDTO dto) {
    UniversityDomainModel result = null;

    if (dto != null) {
      result = new UniversityDomainModel();
      result.setId(dto.getId());
      result.setName(dto.getName());
      result.setDescription(dto.getDescription());
      result.setFoundationDate(dto.getFoundationDate());
      result.setStatus(dto.getUniversityStatusDTO().getStatusName());

      if (dto.getStudentDTOs() != null) {
        final Set<StudentDomainModel> students = new HashSet<>();

        for (StudentDTO studentDTO : dto.getStudentDTOs()) {
          StudentDomainModel model = studentDomainModelDTOConverter.convertDTOToModel(studentDTO);
          students.add(model);
        }

        result.setStudents(students);
      }

      result.setCreationInDbDate(dto.getCreationInDB());
      result.setLastUpdateInDbDate(dto.getLastUpdateInDB());
    }

    return result;
  }
}
