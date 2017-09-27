package com.epam.aerl.mentoring.util;

public interface DomainModelDTOConverter <M, D> {
  D convertModelToDTO(M model);
  M convertDTOToModel(D dto);
}
