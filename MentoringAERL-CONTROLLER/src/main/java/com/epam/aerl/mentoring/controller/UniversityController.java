package com.epam.aerl.mentoring.controller;

import static com.epam.aerl.mentoring.type.UniversityStatus.CLOSED;
import static com.epam.aerl.mentoring.type.UniversityStatus.PENDING_GOVERNMENT_APPROVAL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.epam.aerl.mentoring.entity.AddNewUniversityRequest;
import com.epam.aerl.mentoring.entity.AddNewUniversityResponse;
import com.epam.aerl.mentoring.entity.AssignPair;
import com.epam.aerl.mentoring.entity.GetNotAssignedStudentsResponse;
import com.epam.aerl.mentoring.entity.GetStudentsOfUniversityResponse;
import com.epam.aerl.mentoring.entity.ResponseErrorWithMessage;
import com.epam.aerl.mentoring.entity.SetUniversitiesWarning;
import com.epam.aerl.mentoring.entity.SetUniversityStatusRequest;
import com.epam.aerl.mentoring.entity.SetUniversityStatusResponse;
import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.University;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.entity.Warning;
import com.epam.aerl.mentoring.entity.WarningWithMessage;
import com.epam.aerl.mentoring.exception.ServiceLayerException;
import com.epam.aerl.mentoring.service.UniversityService;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/universities")
public class UniversityController {
  private static final Logger LOG = LogManager.getLogger(UniversityController.class);

  private static final String UNIVERSITY_STATUS_WARNING = "University has status 'Closed' or 'Pending Government Approval'.";
  private static final String NOT_UPDATE_UNIVERSITY_WARNING = "Cannot find universities for ids provided in request. Please " +
      "check ypu input and try again later.";
  private static final String NULL_REQUEST_DATA_ERROR = "Request data is null. Please check your input and try again later.";
  private static final String CREATE_UNIVERSITY_FAIL_ERROR = "Cannot create university. Please check your input and try again later.";

  @Autowired
  @Qualifier("universityService")
  private UniversityService universityService;

  @Autowired
  @Qualifier("dozerBeanMapper")
  private Mapper mapper;

  @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public AddNewUniversityResponse addNewUniversity(@RequestBody AddNewUniversityRequest request) {
    final AddNewUniversityResponse response = new AddNewUniversityResponse();
    final University newUniversity = request.getData();

    if (newUniversity != null) {
      try {
        final UniversityDomainModel model = universityService.createNewUniversity(mapper.map(newUniversity, UniversityDomainModel.class));

        if (model != null) {
          final University createdUniversity = mapper.map(model, University.class);
          response.setData(createdUniversity);
        } else {
          response.addError(new ResponseErrorWithMessage(CREATE_UNIVERSITY_FAIL_ERROR));
        }
      } catch (ServiceLayerException e) {
        LOG.error(e);
        response.addError(new ResponseErrorWithMessage(ErrorMessage.getByCode(e.getCode()).getMessage()));
      }
    } else {
      response.addError(new ResponseErrorWithMessage(NULL_REQUEST_DATA_ERROR));
    }

    return response;
  }

  @GetMapping(value = "{id}/students", produces = APPLICATION_JSON_VALUE)
  @ResponseBody
    public GetStudentsOfUniversityResponse getStudentsOfUniversity(@PathVariable Long id) {
    final GetStudentsOfUniversityResponse response = new GetStudentsOfUniversityResponse();

    if (id != null) {
      try {
        final Map<UniversityStatus, List<StudentDomainModel>> founded = universityService.findStudentsByUniversityId(id);

        for (Map.Entry<UniversityStatus, List<StudentDomainModel>> entry : founded.entrySet()) {
          if (CLOSED.equals(entry.getKey()) || PENDING_GOVERNMENT_APPROVAL.equals(entry.getKey())) {
            response.addWarning(new WarningWithMessage(UNIVERSITY_STATUS_WARNING));
          }

          final List<Student> foundedStudents = new ArrayList<>();
          entry.getValue().forEach(model -> foundedStudents.add(mapper.map(model, Student.class)));
          response.setData(foundedStudents);
        }
      } catch (ServiceLayerException e) {
        LOG.error(e);
        response.addError(new ResponseErrorWithMessage(ErrorMessage.getByCode(e.getCode()).getMessage()));
      }
    } else {
      response.addError(new ResponseErrorWithMessage(NULL_REQUEST_DATA_ERROR));
    }

    return response;
  }

  @GetMapping(value = "/notAssigned", produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public GetNotAssignedStudentsResponse getNotAssignedStudents() {
    final GetNotAssignedStudentsResponse response = new GetNotAssignedStudentsResponse();

    final Map<UniversityDomainModel, List<StudentDomainModel>> founded = universityService.findNotAssignedStudents();
    final List<AssignPair> resultPairs = new ArrayList<>();

    for(Map.Entry<UniversityDomainModel, List<StudentDomainModel>> entry : founded.entrySet()) {
      University university = null;

      if (entry.getKey() != null) {
        university = mapper.map(entry.getKey(), University.class);
      }

      final List<Student> students = new ArrayList<>();
      entry.getValue().forEach(model -> students.add(mapper.map(model, Student.class)));

      resultPairs.add(new AssignPair(university, students));
    }

    response.setData(resultPairs);

    return response;
  }

  @PutMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @ResponseBody
  public SetUniversityStatusResponse setUniversityStatus(@RequestBody SetUniversityStatusRequest request) {
    final SetUniversityStatusResponse response = new SetUniversityStatusResponse();

    if (request.getData() != null) {
      final Map<String, UniversityStatus> updatedUniversitiesResponseList = new HashMap<>();

      for (Map.Entry<UniversityStatus, List<University>> entry : request.getData().entrySet()) {
        List<UniversityDomainModel> models = new ArrayList<>();
        entry.getValue().forEach(provided -> models.add(mapper.map(provided, UniversityDomainModel.class)));
        final List<UniversityDomainModel> updatedUniversities = universityService.setUniversitiesStatus(entry.getKey(), models);

        if (updatedUniversities.size() != models.size()) {
          Iterator iterator = models.iterator();

          while (iterator.hasNext()) {
            UniversityDomainModel model = (UniversityDomainModel) iterator.next();

            for (UniversityDomainModel updatedModel : updatedUniversities) {
              if (model.getId().equals(updatedModel.getId())) {
                iterator.remove();
              }
            }
          }

          final List<Long> warningIds = new ArrayList<>();
          models.forEach(model -> warningIds.add(model.getId()));

          response.addWarning(new SetUniversitiesWarning(NOT_UPDATE_UNIVERSITY_WARNING, warningIds));
        }

        updatedUniversities.forEach(updated -> updatedUniversitiesResponseList.put(updated.getName(), updated.getStatus()));
      }

      response.setData(updatedUniversitiesResponseList);
    } else {
      response.addError(new ResponseErrorWithMessage(NULL_REQUEST_DATA_ERROR));
    }

    return response;
  }
}
