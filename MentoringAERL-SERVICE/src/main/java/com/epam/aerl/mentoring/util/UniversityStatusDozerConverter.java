package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.UniversityStatusDTO;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.dozer.DozerConverter;

public class UniversityStatusDozerConverter extends DozerConverter<UniversityStatusDTO, UniversityStatus> {
  public UniversityStatusDozerConverter() {
    super(UniversityStatusDTO.class, UniversityStatus.class);
  }

  @Override
  public UniversityStatus convertTo(final UniversityStatusDTO source, final UniversityStatus destination) {
    UniversityStatus result = null;

    if (source != null) {
      result = source.getStatusName();
    }

    return result;
  }

  @Override
  public UniversityStatusDTO convertFrom(final UniversityStatus source, final UniversityStatusDTO destination) {
    UniversityStatusDTO result = null;

    if (source != null) {
      result = new UniversityStatusDTO();
      result.setId((long) (source.ordinal() + 1));
      result.setStatusName(source);
    }

    return result;
  }
}
