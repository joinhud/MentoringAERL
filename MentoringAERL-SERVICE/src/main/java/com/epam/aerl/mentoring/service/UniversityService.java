package com.epam.aerl.mentoring.service;

import static com.epam.aerl.mentoring.type.UniversityStatus.CLOSED;
import static com.epam.aerl.mentoring.type.UniversityStatus.PENDING_GOVERNMENT_APPROVAL;

import com.epam.aerl.mentoring.dao.StudentDao;
import com.epam.aerl.mentoring.dao.UniversityDao;
import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.exception.ServiceLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("universityService")
public class UniversityService {
  private static final String WRITE_TO_DB_ERR_MSG = "Can not write data to database.";
  private static final String CANNOT_FIND_UNIVERSITY_ERR_MSG = "Can not find university by specific id in database.";

  private static final Logger LOG = LogManager.getLogger(UniversityService.class);

  @Autowired
  @Qualifier("universityDaoImpl")
  private UniversityDao universityDao;

  @Autowired
  @Qualifier("studentDaoImpl")
  private StudentDao studentDao;

  @Autowired
  @Qualifier("dozerBeanMapper")
  private Mapper mapper;

  public void setUniversityDao(UniversityDao universityDao) {
    this.universityDao = universityDao;
  }

  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  public void setMapper(Mapper mapper) {
    this.mapper = mapper;
  }

  public UniversityDomainModel createNewUniversity(final UniversityDomainModel university) throws ServiceLayerException {
    UniversityDomainModel result = null;

    if (university != null) {
      try {
        final UniversityDTO createdUniversity = universityDao.create(mapper.map(university, UniversityDTO.class));

        if (createdUniversity != null) {
          result = mapper.map(createdUniversity, UniversityDomainModel.class);
        }
      } catch (DaoLayerException e) {
        LOG.error(e);
        throw new ServiceLayerException(ErrorMessage.FAILED_ACCESS_TO_DB.getCode(), WRITE_TO_DB_ERR_MSG, e);
      }
    }

    return result;
  }

  public List<StudentDomainModel> findStudentsByUniversityId(final Long universityId) throws ServiceLayerException {
    List<StudentDomainModel> result = null;

    if (universityId != null) {
      final UniversityDTO foundedUniversity = universityDao.findById(universityId);

      if (foundedUniversity != null) {

        if (!CLOSED.equals(foundedUniversity.getUniversityStatusDTO().getStatusName())
            && !PENDING_GOVERNMENT_APPROVAL.equals(foundedUniversity.getUniversityStatusDTO().getStatusName())) {
          final UniversityDomainModel foundedUniversityModel = mapper.map(foundedUniversity, UniversityDomainModel.class);

          if (foundedUniversityModel.getStudents() != null) {
            result = new ArrayList<>(foundedUniversityModel.getStudents());
          } else {
            result = new ArrayList<>();
          }
        }
      } else {
        throw new ServiceLayerException(ErrorMessage.INCORRECT_UNIVERSITY_ID.getCode(), CANNOT_FIND_UNIVERSITY_ERR_MSG);
      }
    }

    return result;
  }

  public Map<UniversityStatus, List<StudentDomainModel>> findNotAssignedStudents() {
    final Map<UniversityStatus, List<StudentDomainModel>> result = new HashMap<>();

    List<StudentDTO> notAssigned = studentDao.findNotAssignedStudents();
    List<UniversityDTO> closedUniversities = universityDao.findUniversitiesByStatus(UniversityStatus.CLOSED);
    List<UniversityDTO> pendingGovernmentAppUniversities = universityDao.findUniversitiesByStatus(UniversityStatus.PENDING_GOVERNMENT_APPROVAL);

    if (notAssigned != null) {
      List<StudentDomainModel> notAssignedModels = new ArrayList<>();

      for (StudentDTO studentDTO : notAssigned) {
        StudentDomainModel model = mapper.map(studentDTO, StudentDomainModel.class);

        if (model != null) {
          notAssignedModels.add(model);
        }
      }

      if (CollectionUtils.isNotEmpty(notAssignedModels)) {
        result.put(null, notAssignedModels);
      }
    }

    if (closedUniversities != null) {
      List<StudentDomainModel> assignedToClosed = new ArrayList<>();

      for (UniversityDTO universityDTO : closedUniversities) {
        UniversityDomainModel closedModel = mapper.map(universityDTO, UniversityDomainModel.class);

        if (closedModel.getStudents() != null) {
          assignedToClosed.addAll(closedModel.getStudents());
        }
      }

      if (CollectionUtils.isNotEmpty(assignedToClosed)) {
        result.put(UniversityStatus.CLOSED, assignedToClosed);
      }
    }

    if (pendingGovernmentAppUniversities != null) {
      List<StudentDomainModel> assignedToPendingGovernmentApproval = new ArrayList<>();

      for (UniversityDTO universityDTO : pendingGovernmentAppUniversities) {
        UniversityDomainModel pendingModel = mapper.map(universityDTO, UniversityDomainModel.class);

        if (pendingModel.getStudents() != null) {
          assignedToPendingGovernmentApproval.addAll(pendingModel.getStudents());
        }
      }

      if (CollectionUtils.isNotEmpty(assignedToPendingGovernmentApproval)) {
        result.put(UniversityStatus.PENDING_GOVERNMENT_APPROVAL, assignedToPendingGovernmentApproval);
      }
    }

    return result;
  }

  public List<UniversityDomainModel> setUniversitiesStatus(final UniversityStatus newStatus, final List<UniversityDomainModel> universities) {
    List<UniversityDomainModel> result = null;

    if (newStatus != null && universities != null) {
      result = new ArrayList<>();

      for (UniversityDomainModel university : universities) {
        university.setStatus(newStatus);
        final UniversityDTO universityDTO = mapper.map(university, UniversityDTO.class);

        try {
          final UniversityDTO updatedDTO = universityDao.update(universityDTO);
          result.add(mapper.map(updatedDTO, UniversityDomainModel.class));
        } catch (DaoLayerException e) {
          LOG.warn(e);
        }
      }
    }

    return result;
  }
}
