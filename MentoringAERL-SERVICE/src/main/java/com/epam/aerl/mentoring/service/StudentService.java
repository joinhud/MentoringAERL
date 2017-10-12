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
import com.epam.aerl.mentoring.exception.StudentClassCriteriaException;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.UniversityStatus;
import com.epam.aerl.mentoring.util.CriteriaAnalyser;
import com.epam.aerl.mentoring.util.StudentsGenerator;
import org.apache.commons.lang3.StringUtils;
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

@Service("studentService")
public class StudentService {
  private static final String GENERATION_ERROR_MSG = "Can not generate students by criteria.";
  private static final String WRITE_TO_DB_MSG_ERR = "Can not write data to database.";
  private static final String INCORRECT_NUMBER_OF_STUDENTS_MSG_ERR = "The number of students more than 30.";
  private static final String CONFLICTS_IN_CRITERIA_MSG_ERR = "Criteria string has conflicts.";

  private static final Logger LOG = LogManager.getLogger(StudentService.class);

  @Autowired
  @Qualifier("maxStudentsAmount")
  private Integer maxAmount;

  @Autowired
  @Qualifier("studentDaoImpl")
  private StudentDao studentDao;

  @Autowired
  @Qualifier("universityDaoImpl")
  private UniversityDao universityDao;

  @Autowired
  @Qualifier("criteriaAnalyser")
  private CriteriaAnalyser criteriaAnalyser;

  @Autowired
  @Qualifier("criteriaStudentsGenerator")
  private StudentsGenerator criteriaStudentsGenerator;

  @Autowired
  @Qualifier("randomStudentsGenerator")
  private StudentsGenerator randomStudentsGenerator;

  @Autowired
  @Qualifier("dozerBeanMapper")
  private Mapper mapper;

  public void setStudentDao(StudentDao studentDao) {
    this.studentDao = studentDao;
  }

  public void setCriteriaAnalyser(CriteriaAnalyser criteriaAnalyser) {
    this.criteriaAnalyser = criteriaAnalyser;
  }

  public void setCriteriaStudentsGenerator(StudentsGenerator criteriaStudentsGenerator) {
    this.criteriaStudentsGenerator = criteriaStudentsGenerator;
  }

  public void setRandomStudentsGenerator(StudentsGenerator randomStudentsGenerator) {
    this.randomStudentsGenerator = randomStudentsGenerator;
  }

  public void setMaxAmount(Integer maxAmount) {
    this.maxAmount = maxAmount;
  }

  public void setMapper(Mapper mapper) {
    this.mapper = mapper;
  }

  public List<StudentDomainModel> generateStudentsByCriteria(final String criteriaString) throws ServiceLayerException {
    List<StudentDomainModel> result = null;

    if (StringUtils.isNotBlank(criteriaString)) {
      Map<String, Integer> parsedCriteria = criteriaAnalyser.parse(criteriaString);

      try {
        parsedCriteria = criteriaAnalyser.validate(parsedCriteria);
      } catch (StudentClassCriteriaException e) {
        LOG.error(e);
        throw new ServiceLayerException(ErrorMessage.CONFLICTS_INPUT_CRITERIA.getCode(), CONFLICTS_IN_CRITERIA_MSG_ERR, e);
      }

      if (parsedCriteria != null) {
        final Integer generationCount = parsedCriteria.get(GenerationClass.S.toString());

        if (generationCount != null && generationCount <= maxAmount) {
          try {
            final List<StudentDomainModel> generatedStudents = criteriaStudentsGenerator.generateStudents(parsedCriteria);

            if (generatedStudents != null) {
              result = new ArrayList<>();

              for (StudentDomainModel student : generatedStudents) {
                final StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
                final StudentDTO savedStudentDTO = studentDao.create(studentDTO);
                result.add(mapper.map(savedStudentDTO, StudentDomainModel.class));
              }
            }
          } catch (StudentsGeneratorException e) {
            LOG.error(e);
            throw new ServiceLayerException(ErrorMessage.GENERATION_ERROR.getCode(), GENERATION_ERROR_MSG, e);
          } catch (DaoLayerException e) {
            LOG.error(e);
            throw new ServiceLayerException(ErrorMessage.FAILED_ACCESS_TO_DB.getCode(), WRITE_TO_DB_MSG_ERR, e);
          }
        } else {
          throw new ServiceLayerException(ErrorMessage.INCORRECT_STUDENTS_GENERATION_NUMBER.getCode(), INCORRECT_NUMBER_OF_STUDENTS_MSG_ERR);
        }
      }
    }

    return result;
  }

  public List<StudentDomainModel> generateCertainAmountOfStudents(final int amount) throws ServiceLayerException {
    List<StudentDomainModel> result = null;

    if (amount <= maxAmount) {
      final Map<String, Integer> criteria = new HashMap<>();
      criteria.put(GenerationClass.S.toString(), amount);

      try {
        final List<StudentDomainModel> generatedStudents = randomStudentsGenerator.generateStudents(criteria);

        if (generatedStudents != null) {
          result = new ArrayList<>();

          for (StudentDomainModel student : generatedStudents) {
            final StudentDTO studentDTO = mapper.map(student, StudentDTO.class);
            final StudentDTO savedStudentDTO = studentDao.create(studentDTO);
            result.add(mapper.map(savedStudentDTO, StudentDomainModel.class));
          }
        }
      } catch (StudentsGeneratorException e) {
        LOG.error(e);
        throw new ServiceLayerException(ErrorMessage.GENERATION_ERROR.getCode(), GENERATION_ERROR_MSG, e);
      } catch (DaoLayerException e) {
        LOG.error(e);
        throw new ServiceLayerException(ErrorMessage.FAILED_ACCESS_TO_DB.getCode(), WRITE_TO_DB_MSG_ERR, e);
      }
    } else {
      throw new ServiceLayerException(ErrorMessage.INCORRECT_STUDENTS_GENERATION_NUMBER.getCode(), INCORRECT_NUMBER_OF_STUDENTS_MSG_ERR);
    }

    return result;
  }

  public List<StudentDomainModel> findStudentsByIds(final List<Long> ids) {
    List<StudentDomainModel> result = null;

    if (ids != null) {
      result = new ArrayList<>();

      final List<StudentDTO> foundedStudents = studentDao.findStudentsByIds(ids);

      if (foundedStudents != null) {
        for (StudentDTO studentDTO : foundedStudents) {
          result.add(mapper.map(studentDTO, StudentDomainModel.class));
        }
      }
    }

    return result;
  }

  public List<Long> removeStudentsByIds(final List<Long> ids) {
    List<Long> result = null;

    if (ids != null) {
      result = new ArrayList<>();

      for (Long id : ids) {
        if (studentDao.deleteById(id)) {
          result.add(id);
        }
      }
    }

    return result;
  }

  public List<Long> assignStudentsToUniversity(final UniversityDomainModel university, final List<StudentDomainModel> students) {
    List<Long> result = null;

    if (students != null && university != null) {
      if (university.getId() != null) {
        result = new ArrayList<>();

        final UniversityDTO universityDTO = universityDao.findById(university.getId());
        final UniversityDomainModel model = mapper.map(universityDTO, UniversityDomainModel.class);

        for (StudentDomainModel student : students) {
          try {
            final UniversityStatus status = model.getStatus();

            if (!CLOSED.equals(status) && !PENDING_GOVERNMENT_APPROVAL.equals(status)) {
              final StudentDTO studentDTO = studentDao.findStudentById(student.getId());
              studentDTO.setUniversityDTO(mapper.map(university, UniversityDTO.class));

              final StudentDTO assignedStudent = studentDao.update(studentDTO);

              if (assignedStudent != null) {
                result.add(assignedStudent.getId());
              }
            }
          } catch (DaoLayerException e) {
            LOG.warn(e);
          }
        }
      }
    }

    return result;
  }

  public List<StudentDomainModel> updateStudentsInformation(final List<StudentDomainModel> students) {
    List<StudentDomainModel> result = null;

    if (students != null) {
      result = new ArrayList<>();

      for (StudentDomainModel student : students) {
        final StudentDTO founded = studentDao.findStudentById(student.getId());

        if (founded != null) {
          final StudentDomainModel foundedStudentModel = mapper.map(founded, StudentDomainModel.class);
          final StudentDomainModel modifiedStudent = compareStudentsModels(foundedStudentModel, student);

          if (modifiedStudent != null) {
            try {
              final StudentDTO updatedStudentDTO = studentDao.update(mapper.map(modifiedStudent, StudentDTO.class));

              if (updatedStudentDTO != null) {
                result.add(mapper.map(updatedStudentDTO, StudentDomainModel.class));
              }
            } catch (DaoLayerException e) {
              LOG.warn(e);
            }
          }
        }
      }
    }

    return result;
  }

  private StudentDomainModel compareStudentsModels(final StudentDomainModel original,
      final StudentDomainModel modificationModel) {
    final StudentDomainModel result = original;

    if (modificationModel.getName() != null) {
      result.setName(modificationModel.getName());
    }

    if (modificationModel.getSurname() != null) {
      result.setSurname(modificationModel.getSurname());
    }

    if (modificationModel.getAge() != null) {
      result.setAge(modificationModel.getAge());
    }

    if (modificationModel.getCourse() != null) {
      result.setCourse(modificationModel.getCourse());
    }

    if (modificationModel.getMarks() != null) {
      result.setMarks(modificationModel.getMarks());
    }

    return result;
  }
}
