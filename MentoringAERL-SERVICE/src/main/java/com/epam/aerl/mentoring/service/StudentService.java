package com.epam.aerl.mentoring.service;

import static com.epam.aerl.mentoring.type.UniversityStatus.CLOSED;
import static com.epam.aerl.mentoring.type.UniversityStatus.PENDING_GOVERNMENT_APPROVAL;

import com.epam.aerl.mentoring.dao.StudentDao;
import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.exception.ServiceLayerException;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.UniversityStatus;
import com.epam.aerl.mentoring.util.CriteriaAnalyser;
import com.epam.aerl.mentoring.util.StudentDomainModelDTOConverter;
import com.epam.aerl.mentoring.util.StudentsGenerator;
import com.epam.aerl.mentoring.util.UniversityDomainModelDTOConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

  private static final Logger LOG = LogManager.getLogger(StudentService.class);

  @Autowired
  @Qualifier("studentDaoImpl")
  private StudentDao studentDao;

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
  @Qualifier("studentDomainModelDTOConverter")
  private StudentDomainModelDTOConverter studentDomainModelDTOConverter;

  @Autowired
  @Qualifier("universityDomainModelDTOConverter")
  private UniversityDomainModelDTOConverter universityDomainModelDTOConverter;

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

  public void setStudentDomainModelDTOConverter(
      StudentDomainModelDTOConverter studentDomainModelDTOConverter) {
    this.studentDomainModelDTOConverter = studentDomainModelDTOConverter;
  }

  public void setUniversityDomainModelDTOConverter(
      UniversityDomainModelDTOConverter universityDomainModelDTOConverter) {
    this.universityDomainModelDTOConverter = universityDomainModelDTOConverter;
  }

  public List<StudentDomainModel> generateStudentsByCriteria(final String criteriaString) throws ServiceLayerException {
    List<StudentDomainModel> result = null;

    if (StringUtils.isNotBlank(criteriaString)) {
      final Map<String, Integer> parsedCriteria = criteriaAnalyser.parse(criteriaString);

      if (parsedCriteria != null) {
        final Integer generationCount = parsedCriteria.get(GenerationClass.S.toString());

        if (generationCount != null && generationCount <= 30) {
          try {
            final List<StudentDomainModel> generatedStudents = criteriaStudentsGenerator.generateStudents(parsedCriteria);

            if (generatedStudents != null) {
              result = new ArrayList<>();

              for (StudentDomainModel student : generatedStudents) {
                final StudentDTO studentDTO = studentDomainModelDTOConverter.convertModelToDTO(student);
                final StudentDTO savedStudentDTO = studentDao.create(studentDTO);
                result.add(studentDomainModelDTOConverter.convertDTOToModel(savedStudentDTO));
              }
            }
          } catch (StudentsGeneratorException e) {
            throw new ServiceLayerException(ErrorMessage.GENERATION_ERROR.getCode(), GENERATION_ERROR_MSG, e);
          } catch (DaoLayerException e) {
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

    if (amount <= 30) {
      final Map<String, Integer> criteria = new HashMap<>();
      criteria.put(GenerationClass.S.toString(), amount);

      try {
        final List<StudentDomainModel> generatedStudents = randomStudentsGenerator.generateStudents(criteria);

        if (generatedStudents != null) {
          result = new ArrayList<>();

          for (StudentDomainModel student : generatedStudents) {
            final StudentDTO studentDTO = studentDomainModelDTOConverter.convertModelToDTO(student);
            final StudentDTO savedStudentDTO = studentDao.create(studentDTO);
            result.add(studentDomainModelDTOConverter.convertDTOToModel(savedStudentDTO));
          }
        }
      } catch (StudentsGeneratorException e) {
        throw new ServiceLayerException(ErrorMessage.GENERATION_ERROR.getCode(), GENERATION_ERROR_MSG, e);
      } catch (DaoLayerException e) {
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

      for (Long id : ids) {
        final StudentDTO foundedStudent = studentDao.findStudentById(id);

        if (foundedStudent != null) {
          result.add(studentDomainModelDTOConverter.convertDTOToModel(foundedStudent));
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
      result = new ArrayList<>();

      for (StudentDomainModel student : students) {
        try {
          final UniversityStatus status = university.getStatus();

          if (!CLOSED.equals(status) && !PENDING_GOVERNMENT_APPROVAL.equals(status)) {
            final StudentDTO studentDTO = studentDomainModelDTOConverter.convertModelToDTO(student);
            studentDTO.setUniversityDTO(universityDomainModelDTOConverter.convertModelToDTO(university));

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

    return result;
  }

  public List<StudentDomainModel> updateStudentsInformation(final List<StudentDomainModel> students) {
    List<StudentDomainModel> result = null;

    if (students != null) {
      result = new ArrayList<>();

      for (StudentDomainModel student : students) {
        final StudentDTO founded = studentDao.findStudentById(student.getId());
        final StudentDomainModel foundedStudentModel = studentDomainModelDTOConverter.convertDTOToModel(founded);

        final StudentDomainModel modifiedStudent = compareStudentsModels(foundedStudentModel, student);

        if (modifiedStudent != null) {
          try {
            final StudentDTO updatedStudentDTO = studentDao.update(studentDomainModelDTOConverter.convertModelToDTO(modifiedStudent));

            if (updatedStudentDTO != null) {
              result.add(studentDomainModelDTOConverter.convertDTOToModel(updatedStudentDTO));
            }
          } catch (DaoLayerException e) {
            LOG.warn(e);
          }
        }
      }
    }

    return result;
  }

  private StudentDomainModel compareStudentsModels(final StudentDomainModel original, final StudentDomainModel modificationModel) {
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
