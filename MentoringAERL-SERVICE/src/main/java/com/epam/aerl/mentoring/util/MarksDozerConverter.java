package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentMarkDTO;
import com.epam.aerl.mentoring.type.Subject;
import org.dozer.DozerConverter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MarksDozerConverter extends DozerConverter<Set, Map> {

  public MarksDozerConverter() {
    super(Set.class, Map.class);
  }

  @Override
  public Map<Subject, Integer> convertTo(final Set source, final Map destination) {
    Map<Subject, Integer> result = null;

    if (source != null) {
      result = new HashMap<>();

      for (Object markDTO : source) {
        result.put(((StudentMarkDTO)markDTO).getSubject(), ((StudentMarkDTO)markDTO).getMark());
      }
    }

    return result;
  }

  @Override
  public Set<StudentMarkDTO> convertFrom(final Map source, final Set destination) {
    Set<StudentMarkDTO> result = null;

    if (source != null) {
      result = new HashSet<>();

      for (Object mark : source.entrySet()) {
        StudentMarkDTO markDTO = new StudentMarkDTO();
        markDTO.setSubject(((Map.Entry<Subject, Integer>)mark).getKey());
        markDTO.setMark(((Map.Entry<Subject, Integer>)mark).getValue());
        result.add(markDTO);
      }
    }

    return result;
  }
}
