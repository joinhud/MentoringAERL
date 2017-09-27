package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.StudentMarkDTO;
import com.epam.aerl.mentoring.type.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service("studentDomainModelDTOConverter")
public class StudentDomainModelDTOConverter implements DomainModelDTOConverter<StudentDomainModel, StudentDTO> {

  @Override
  public StudentDTO convertModelToDTO(final StudentDomainModel model) {
    StudentDTO result = null;

    if (model != null) {
      result = new StudentDTO();
      result.setId(model.getId());
      result.setName(model.getName());
      result.setSurname(model.getSurname());
      result.setAge(model.getAge());
      result.setCourse(model.getCourse());

      if (model.getMarks() != null) {
        final Set<StudentMarkDTO> markDTOs = new HashSet<>();

        for (Map.Entry<Subject, Integer> mark : model.getMarks().entrySet()) {
          StudentMarkDTO markDTO = new StudentMarkDTO();
          markDTO.setSubject(mark.getKey());
          markDTO.setMark(mark.getValue());

          markDTOs.add(markDTO);
        }

        result.setMarks(markDTOs);
      }

      result.setCreationInBD(model.getCreationInDbDate());
      result.setLastUpdateInBD(model.getLastUpdateInDbDate());
    }

    return result;
  }

  @Override
  public StudentDomainModel convertDTOToModel(final StudentDTO dto) {
    StudentDomainModel result = null;

    if (dto != null) {
      result = new StudentDomainModel();
      result.setId(dto.getId());
      result.setName(dto.getName());
      result.setSurname(dto.getSurname());
      result.setAge(dto.getAge());
      result.setCourse(dto.getCourse());

      if (dto.getMarks() != null) {
        final Map<Subject, Integer> marks = new HashMap<>();

        for (StudentMarkDTO mark : dto.getMarks()) {
          marks.put(mark.getSubject(), mark.getMark());
        }

        result.setMarks(marks);
      }

      result.setCreationInDbDate(dto.getCreationInBD());
      result.setLastUpdateInDbDate(dto.getLastUpdateInBD());
    }

    return result;
  }
}
