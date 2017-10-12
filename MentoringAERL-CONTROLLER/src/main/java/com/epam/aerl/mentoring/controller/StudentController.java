package com.epam.aerl.mentoring.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.epam.aerl.mentoring.entity.AssignPair;
import com.epam.aerl.mentoring.entity.AssignStudentsRequest;
import com.epam.aerl.mentoring.entity.AssignStudentsResponse;
import com.epam.aerl.mentoring.entity.FindStudentsByIdsWarning;
import com.epam.aerl.mentoring.entity.FindStudentsResponse;
import com.epam.aerl.mentoring.entity.GenerateStudentsRequest;
import com.epam.aerl.mentoring.entity.GenerateStudentsResponse;
import com.epam.aerl.mentoring.entity.MentoringAERLResponse;
import com.epam.aerl.mentoring.entity.RemoveStudentsRequest;
import com.epam.aerl.mentoring.entity.RemoveStudentsResponse;
import com.epam.aerl.mentoring.entity.ResponseErrorWithMessage;
import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.entity.UpdateStudentsRequest;
import com.epam.aerl.mentoring.entity.UpdateStudentsResponse;
import com.epam.aerl.mentoring.exception.ServiceLayerException;
import com.epam.aerl.mentoring.service.StudentService;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
  private static final Logger LOG = LogManager.getLogger(StudentController.class);
  private static final String NO_ONE_VALID_ID_FOUNDED = "Service was no find any one valid Student for ids provided in request. " +
      "Please verify your input students ids.";
  private static final String NOT_FIND_STUDENTS_BY_IDS_WARNING = "Student was not find for ids provided in request.";
  private static final String NOT_REMOVED_STUDENTS_BY_IDS_ERROR = "Cannot find or remove students by ids provided in request.";
  private static final String NOT_REMOVED_STUDENTS_BY_IDS_WARNING = "Failed to find or remove students for som of student's " +
      "ids provided in request.";
  private static final String NOT_ASSIGNED_STUDENTS_TO_UNIVERSITY_WARNING = "Some of students do not exists or some of " +
      "universities do not exists or some of universities are closed or in status 'Pending Government Approval'.";
  private static final String NOT_UPDATE_STUDENTS_INFORMATION_ERROR = "Cannot update provided students information. " +
      "Please check your input and try again later.";
  private static final String NOT_UPDATE_STUDENTS_INFORMATION_WARNING = "Some of provided students cannot be updated. " +
      "Please check your input and try again later.";
  private static final String NULL_REQUEST_DATA_ERROR = "Request data is null. Please check your input and try again later.";

  @Autowired
  @Qualifier("studentService")
  private StudentService studentService;

  @Autowired
  @Qualifier("dozerBeanMapper")
  private Mapper mapper;

  @PostMapping(value = "/{criteriaString}", produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public GenerateStudentsResponse generateStudentByCriteria(@PathVariable String criteriaString)
      throws ServiceLayerException {
    final GenerateStudentsResponse response = new GenerateStudentsResponse();

    final List<StudentDomainModel> generatedStudents = studentService.generateStudentsByCriteria(criteriaString);

    if (generatedStudents != null) {
      final List<Student> resultList = new ArrayList<>();

      for (StudentDomainModel model : generatedStudents) {
        resultList.add(mapper.map(model, Student.class));
      }

      response.setData(resultList);
    }

    return response;
  }

  @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public GenerateStudentsResponse generateStudentsByStudentsNumber(@RequestBody GenerateStudentsRequest request)
      throws ServiceLayerException {
    final GenerateStudentsResponse response = new GenerateStudentsResponse();

    final List<StudentDomainModel> generatedStudents = studentService.generateCertainAmountOfStudents(request.getData());

    if (generatedStudents != null) {
      final List<Student> resultList = new ArrayList<>();

      for (StudentDomainModel model : generatedStudents) {
        resultList.add(mapper.map(model, Student.class));
      }

      response.setData(resultList);
    }

    return response;
  }

  @GetMapping(value = "/{ids}", produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public FindStudentsResponse findStudentsById(@PathVariable List<Long> ids) {
    final FindStudentsResponse response = new FindStudentsResponse();
    final List<StudentDomainModel> foundedStudents = studentService.findStudentsByIds(ids);

    if (CollectionUtils.isEmpty(foundedStudents)) {
      response.addError(new ResponseErrorWithMessage(NO_ONE_VALID_ID_FOUNDED));
    } else {
      if (ids.size() != foundedStudents.size()) {
        foundedStudents.forEach(studentDomainModel -> ids.removeIf(i -> i.equals(studentDomainModel.getId())));
        response.addWarning(new FindStudentsByIdsWarning(NOT_FIND_STUDENTS_BY_IDS_WARNING, ids));
      }

      final List<Student> students = new ArrayList<>();
      foundedStudents.forEach(model -> students.add(mapper.map(model, Student.class)));
      response.setData(students);
    }

    return response;
  }

  @DeleteMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public RemoveStudentsResponse removeStudentsBySpecificIds(@RequestBody RemoveStudentsRequest request) {
    final RemoveStudentsResponse response = new RemoveStudentsResponse();

    final List<Long> removedStudentsIds = studentService.removeStudentsByIds(request.getData());

    if (CollectionUtils.isEmpty(removedStudentsIds)) {
      response.addError(new ResponseErrorWithMessage(NOT_REMOVED_STUDENTS_BY_IDS_ERROR));
    } else {
      if (request.getData().size() != removedStudentsIds.size()) {
        request.getData().removeAll(removedStudentsIds);
        response.addWarning(new FindStudentsByIdsWarning(NOT_REMOVED_STUDENTS_BY_IDS_WARNING, request.getData()));
      }

      response.setData(removedStudentsIds);
    }

    return response;
  }

  @PutMapping(value = "/assign/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public AssignStudentsResponse assignStudentsToUniversities(@RequestBody AssignStudentsRequest request) {
    final AssignStudentsResponse response = new AssignStudentsResponse();
    final List<Long> notAssignedIds = new ArrayList<>();
    final List<Long> resultListIds = new ArrayList<>();

    for (AssignPair assignPair : request.getData()) {
      final UniversityDomainModel universityModel = mapper.map(assignPair.getUniversity(), UniversityDomainModel.class);
      final List<StudentDomainModel> studentModels = new ArrayList<>();

      for (Student student : assignPair.getStudents()) {
        studentModels.add(mapper.map(student, StudentDomainModel.class));
      }

      final List<Long> assignedStudentsIds = studentService.assignStudentsToUniversity(universityModel, studentModels);

      if (assignedStudentsIds.size() != assignPair.getStudents().size()) {
        for (Student student : assignPair.getStudents()) {
          if (!assignedStudentsIds.contains(student.getId())) {
            notAssignedIds.add(student.getId());
          }
        }
      }

      resultListIds.addAll(assignedStudentsIds);
    }

    if (CollectionUtils.isNotEmpty(notAssignedIds)) {
      response.addWarning(new FindStudentsByIdsWarning(NOT_ASSIGNED_STUDENTS_TO_UNIVERSITY_WARNING, notAssignedIds));
    }

    if (CollectionUtils.isNotEmpty(resultListIds)) {
      response.setData(resultListIds);
    }

    return response;
  }

  @PutMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public UpdateStudentsResponse updateStudents(@RequestBody UpdateStudentsRequest request) {
    final UpdateStudentsResponse response = new UpdateStudentsResponse();

    final List<StudentDomainModel> studentModels = new ArrayList<>();

    if (request.getData() != null) {
      for (Student student : request.getData()) {
        studentModels.add(mapper.map(student, StudentDomainModel.class));
      }

      final List<StudentDomainModel> updatedStudents = studentService.updateStudentsInformation(studentModels);

      if (CollectionUtils.isEmpty(updatedStudents)) {
        response.addError(new ResponseErrorWithMessage(NOT_UPDATE_STUDENTS_INFORMATION_ERROR));
      } else {
        if (studentModels.size() != updatedStudents.size()) {
          final List<Long> notUpdatedStudentsIds = new ArrayList<>();

          for (StudentDomainModel model : studentModels) {
            if (!updatedStudents.contains(model)) {
              notUpdatedStudentsIds.add(model.getId());
            }
          }

          response.addWarning(new FindStudentsByIdsWarning(NOT_UPDATE_STUDENTS_INFORMATION_WARNING, notUpdatedStudentsIds));
        }

        final List<Student> resultList = new ArrayList<>();

        for (StudentDomainModel model : updatedStudents) {
          resultList.add(mapper.map(model, Student.class));
        }

        response.setData(resultList);
      }
    } else {
      response.addError(new ResponseErrorWithMessage(NULL_REQUEST_DATA_ERROR));
    }

    return response;
  }

  @ExceptionHandler(ServiceLayerException.class)
  public ResponseEntity handleServiceLayerException(ServiceLayerException e) {
    LOG.error(e);

    ResponseEntity<MentoringAERLResponse> result;
    final MentoringAERLResponse response = new MentoringAERLResponse();
    response.addError(new ResponseErrorWithMessage(ErrorMessage.getByCode(e.getCode()).getMessage()));

    switch (ErrorMessage.getByCode(e.getCode())) {
      case INCORRECT_STUDENTS_GENERATION_NUMBER: {
        result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        break;
      }
      case GENERATION_ERROR: {
        result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        break;
      }
      case FAILED_ACCESS_TO_DB: {
        result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        break;
      }
      default: {
        result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }
    }

    return result;
  }
}
