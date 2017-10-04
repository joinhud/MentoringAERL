package com.epam.aerl.mentoring.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.epam.aerl.mentoring.entity.AddNewUniversityRequest;
import com.epam.aerl.mentoring.entity.AddNewUniversityResponse;
import com.epam.aerl.mentoring.entity.GetStudentsOfUniversityResponse;
import com.epam.aerl.mentoring.entity.SetUniversityStatusRequest;
import com.epam.aerl.mentoring.entity.SetUniversityStatusResponse;
import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.University;
import com.epam.aerl.mentoring.entity.UniversityDomainModel;
import com.epam.aerl.mentoring.exception.ServiceLayerException;
import com.epam.aerl.mentoring.service.UniversityService;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UniversityControllerTest {

  @Mock
  private UniversityService universityService;

  @Mock
  private Mapper mapper;

  @InjectMocks
  private UniversityController universityController;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    universityController = new UniversityController();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testAddNewUniversity_NullRequestData() {
    // Given
    final AddNewUniversityRequest dummyRequest = new AddNewUniversityRequest();
    final University expectedData = null;
    final int expectedErrorsCount = 1;

    // When
    final AddNewUniversityResponse actual = universityController.addNewUniversity(dummyRequest);

    // Then
    assertThat(actual.getData(), is(expectedData));
    assertThat(actual.getErrors().size(), is(expectedErrorsCount));
  }

  @Test
  public void testAddNewUniversity_EmptyRequestData() throws ServiceLayerException {
    // Given
    final AddNewUniversityRequest dummyRequest = new AddNewUniversityRequest();
    final University dummyRequestData = new University();
    final UniversityDomainModel dummyUniversityDomainModel = new UniversityDomainModel();
    final University expectedResponseData = null;
    final int expectedResponseErrorsCount = 1;
    dummyRequest.setData(dummyRequestData);

    // When
    when(mapper.map(dummyRequestData, UniversityDomainModel.class)).thenReturn(dummyUniversityDomainModel);
    when(universityService.createNewUniversity(dummyUniversityDomainModel)).thenReturn(null);
    final AddNewUniversityResponse actualResponse = universityController.addNewUniversity(dummyRequest);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
  }

  @Test
  public void testAddNewUniversity_DatabaseError() throws ServiceLayerException {
    // Given
    final AddNewUniversityRequest dummyRequest = new AddNewUniversityRequest();
    final University dummyRequestData = new University();
    dummyRequestData.setName("Test");
    dummyRequestData.setDescription("Test");
    dummyRequestData.setFoundationDate(LocalDate.of(1966, 4, 15));
    dummyRequestData.setStatus(UniversityStatus.OPEN);
    dummyRequest.setData(dummyRequestData);
    final UniversityDomainModel dummyUniversityDomainModel = new UniversityDomainModel();
    dummyUniversityDomainModel.setName("Test");
    dummyUniversityDomainModel.setDescription("Test");
    dummyUniversityDomainModel.setFoundationDate(LocalDate.of(1966, 4, 15));
    dummyUniversityDomainModel.setStatus(UniversityStatus.OPEN);
    final ServiceLayerException dummyException = new ServiceLayerException("1", "");
    final University expectedResponseData = null;
    final int expectedResponseErrorsCount = 1;

    // When
    when(mapper.map(dummyRequestData, UniversityDomainModel.class)).thenReturn(dummyUniversityDomainModel);
    when(universityService.createNewUniversity(dummyUniversityDomainModel)).thenThrow(dummyException);
    final AddNewUniversityResponse actualResponse = universityController.addNewUniversity(dummyRequest);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
  }

  @Test
  public void testAddNewUniversity_CorrectRequest() throws ServiceLayerException {
    // Given
    final AddNewUniversityRequest request = new AddNewUniversityRequest();
    final University requestData = new University();
    requestData.setName("Test");
    requestData.setDescription("Test");
    requestData.setFoundationDate(LocalDate.of(1966, 4, 15));
    requestData.setStatus(UniversityStatus.OPEN);
    request.setData(requestData);
    final UniversityDomainModel mapperReturnedDomainModel = new UniversityDomainModel();
    mapperReturnedDomainModel.setName("Test");
    mapperReturnedDomainModel.setDescription("Test");
    mapperReturnedDomainModel.setFoundationDate(LocalDate.of(1966, 4, 15));
    mapperReturnedDomainModel.setStatus(UniversityStatus.OPEN);
    final UniversityDomainModel serviceReturnedDomainModel = new UniversityDomainModel();
    serviceReturnedDomainModel.setId(1L);
    serviceReturnedDomainModel.setName("Test");
    serviceReturnedDomainModel.setDescription("Test");
    serviceReturnedDomainModel.setFoundationDate(LocalDate.of(1966, 4, 15));
    serviceReturnedDomainModel.setStatus(UniversityStatus.OPEN);
    final University expectedResponseData = new University();
    expectedResponseData.setId(1L);
    expectedResponseData.setName("Test");
    expectedResponseData.setDescription("Test");
    expectedResponseData.setFoundationDate(LocalDate.of(1966, 4, 15));
    expectedResponseData.setStatus(UniversityStatus.OPEN);
    final int expectedResponseErrorsCount = 0;

    // When
    when(mapper.map(requestData, UniversityDomainModel.class)).thenReturn(mapperReturnedDomainModel);
    when(mapper.map(serviceReturnedDomainModel, University.class)).thenReturn(expectedResponseData);
    when(universityService.createNewUniversity(mapperReturnedDomainModel)).thenReturn(serviceReturnedDomainModel);
    final AddNewUniversityResponse expectedResponse = universityController.addNewUniversity(request);

    // Then
    assertThat(expectedResponse.getData(), is(expectedResponseData));
    assertThat(expectedResponse.getErrors().size(), is(expectedResponseErrorsCount));
  }

  @Test
  public void testGetStudentsOfUniversity_NullRequestData() {
    // Given
    final Long dummyId = null;
    final List<Student> expectedResponseData = null;
    final int expectedResponseErrorsCount = 1;

    // When
    final GetStudentsOfUniversityResponse actualResponse = universityController.getStudentsOfUniversity(dummyId);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
  }

  @Test
  public void testGetStudentsOfUniversity_NotExistId() throws ServiceLayerException {
    // Given
    final Long id = -10000L;
    final ServiceLayerException returnedException = new ServiceLayerException("1", "");
    final List<Student> expectedResponseData = null;
    final int expectedResponseErrorsCount = 1;

    // When
    when(universityService.findStudentsByUniversityId(id)).thenThrow(returnedException);
    final GetStudentsOfUniversityResponse actualResponse = universityController.getStudentsOfUniversity(id);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
  }

  @Test
  public void testGetStudentsOfUniversity_ClosedUniversityId() throws ServiceLayerException {
    // Given
    final Long id = 1L;
    final Map<UniversityStatus, List<StudentDomainModel>> serviceReturned = new HashMap<>();
    final StudentDomainModel serviceReturnedStudentModel = new StudentDomainModel();
    serviceReturnedStudentModel.setId(1L);
    serviceReturnedStudentModel.setName("Test");
    serviceReturnedStudentModel.setSurname("Test");
    serviceReturnedStudentModel.setAge(18);
    serviceReturnedStudentModel.setCourse(1);
    final List<StudentDomainModel> serviceReturnedModelsList = new ArrayList<>();
    serviceReturnedModelsList.add(serviceReturnedStudentModel);
    final UniversityStatus returnedServiceStatus = UniversityStatus.CLOSED;
    serviceReturned.put(returnedServiceStatus, serviceReturnedModelsList);
    final List<Student> expectedResponseData = new ArrayList<>();
    final Student expectedStudent = new Student();
    expectedStudent.setId(1L);
    expectedStudent.setName("Test");
    expectedStudent.setSurname("Test");
    expectedStudent.setAge(18);
    expectedStudent.setCourse(1);
    expectedResponseData.add(expectedStudent);
    final int expectedWarningsCount = 1;

    // When
    when(universityService.findStudentsByUniversityId(id)).thenReturn(serviceReturned);
    when(mapper.map(serviceReturnedStudentModel, Student.class)).thenReturn(expectedStudent);
    final GetStudentsOfUniversityResponse actualResponse = universityController.getStudentsOfUniversity(id);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getWarnings().size(), is(expectedWarningsCount));
  }

  @Test
  public void testGetStudentsOfUniversity_PendingGovernmentApprovalUniversityId() throws ServiceLayerException {
    // Given
    final Long id = 1L;
    final Map<UniversityStatus, List<StudentDomainModel>> serviceReturned = new HashMap<>();
    final StudentDomainModel serviceReturnedStudentModel = new StudentDomainModel();
    serviceReturnedStudentModel.setId(1L);
    serviceReturnedStudentModel.setName("Test");
    serviceReturnedStudentModel.setSurname("Test");
    serviceReturnedStudentModel.setAge(18);
    serviceReturnedStudentModel.setCourse(1);
    final List<StudentDomainModel> serviceReturnedModelsList = new ArrayList<>();
    serviceReturnedModelsList.add(serviceReturnedStudentModel);
    final UniversityStatus returnedServiceStatus = UniversityStatus.PENDING_GOVERNMENT_APPROVAL;
    serviceReturned.put(returnedServiceStatus, serviceReturnedModelsList);
    final List<Student> expectedResponseData = new ArrayList<>();
    final Student expectedStudent = new Student();
    expectedStudent.setId(1L);
    expectedStudent.setName("Test");
    expectedStudent.setSurname("Test");
    expectedStudent.setAge(18);
    expectedStudent.setCourse(1);
    expectedResponseData.add(expectedStudent);
    final int expectedWarningsCount = 1;

    // When
    when(universityService.findStudentsByUniversityId(id)).thenReturn(serviceReturned);
    when(mapper.map(serviceReturnedStudentModel, Student.class)).thenReturn(expectedStudent);
    final GetStudentsOfUniversityResponse actualResponse = universityController.getStudentsOfUniversity(id);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getWarnings().size(), is(expectedWarningsCount));
  }

  @Test
  public void testGetStudentsOfUniversity_CorrectRequest() throws ServiceLayerException {
    // Given
    final Long id = 1L;
    final Map<UniversityStatus, List<StudentDomainModel>> serviceReturned = new HashMap<>();
    final StudentDomainModel serviceReturnedStudentModel = new StudentDomainModel();
    serviceReturnedStudentModel.setId(1L);
    serviceReturnedStudentModel.setName("Test");
    serviceReturnedStudentModel.setSurname("Test");
    serviceReturnedStudentModel.setAge(18);
    serviceReturnedStudentModel.setCourse(1);
    final List<StudentDomainModel> serviceReturnedModelsList = new ArrayList<>();
    serviceReturnedModelsList.add(serviceReturnedStudentModel);
    final UniversityStatus returnedServiceStatus = UniversityStatus.OPEN;
    serviceReturned.put(returnedServiceStatus, serviceReturnedModelsList);
    final List<Student> expectedResponseData = new ArrayList<>();
    final Student expectedStudent = new Student();
    expectedStudent.setId(1L);
    expectedStudent.setName("Test");
    expectedStudent.setSurname("Test");
    expectedStudent.setAge(18);
    expectedStudent.setCourse(1);
    expectedResponseData.add(expectedStudent);
    final int expectedWarningsCount = 0;
    final int expectedErrorsCount = 0;

    // When
    when(universityService.findStudentsByUniversityId(id)).thenReturn(serviceReturned);
    when(mapper.map(serviceReturnedStudentModel, Student.class)).thenReturn(expectedStudent);
    final GetStudentsOfUniversityResponse actualResponse = universityController.getStudentsOfUniversity(id);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getWarnings().size(), is(expectedWarningsCount));
    assertThat(actualResponse.getErrors().size(), is(expectedErrorsCount));
  }

  @Test
  public void testSetUniversityStatus_NullRequestParameter() {
    // Given
    final SetUniversityStatusRequest dummyRequest = new SetUniversityStatusRequest();
    final Map<String, UniversityStatus> expectedResponseData = null;
    final int expectedResponseErrorsCount = 1;

    // When
    final SetUniversityStatusResponse actualResponse = universityController.setUniversityStatus(dummyRequest);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
  }

  @Test
  public void testSetUniversityStatus_RequestWithNotExistingUniversities() {
    // Given
    final SetUniversityStatusRequest request = new SetUniversityStatusRequest();
    final UniversityStatus status = UniversityStatus.OPEN;
    final University notExistingUniversity = new University();
    notExistingUniversity.setId(-500L);
    notExistingUniversity.setName("Test");
    final University existingUniversity = new University();
    existingUniversity.setId(1L);
    existingUniversity.setName("TEST");
    final List<University> requestedUniversities = new ArrayList<>();
    requestedUniversities.add(notExistingUniversity);
    requestedUniversities.add(existingUniversity);
    final Map<UniversityStatus, List<University>> requestData = new HashMap<>();
    requestData.put(status, requestedUniversities);
    request.setData(requestData);
    final List<UniversityDomainModel> mappedModels = new ArrayList<>();
    final UniversityDomainModel notExistingMapperReturnedModel = new UniversityDomainModel();
    notExistingMapperReturnedModel.setId(-500L);
    notExistingMapperReturnedModel.setName("Test");
    final UniversityDomainModel existingMapperReturnedModel = new UniversityDomainModel();
    existingMapperReturnedModel.setId(1L);
    existingMapperReturnedModel.setName("TEST");
    mappedModels.add(notExistingMapperReturnedModel);
    mappedModels.add(existingMapperReturnedModel);
    final List<UniversityDomainModel> serviceReturned = new ArrayList<>();
    final UniversityDomainModel serviceReturnedExistingModel = new UniversityDomainModel();
    serviceReturnedExistingModel.setId(1L);
    serviceReturnedExistingModel.setStatus(status);
    serviceReturnedExistingModel.setName("TEST");
    serviceReturned.add(serviceReturnedExistingModel);
    final Map<String, UniversityStatus> expectedResponseData = new HashMap<>();
    expectedResponseData.put("TEST", status);
    final int exceptedWarningsCount = 1;

    // When
    when(mapper.map(notExistingUniversity, UniversityDomainModel.class)).thenReturn(notExistingMapperReturnedModel);
    when(mapper.map(existingUniversity, UniversityDomainModel.class)).thenReturn(existingMapperReturnedModel);
    when(universityService.setUniversitiesStatus(status, mappedModels)).thenReturn(serviceReturned);
    final SetUniversityStatusResponse actualResponse = universityController.setUniversityStatus(request);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getWarnings().size(), is(exceptedWarningsCount));
  }

  @Test
  public void testSetUniversityStatus_CorrectRequestParameters() {
    // Given
    final SetUniversityStatusRequest request = new SetUniversityStatusRequest();
    final UniversityStatus status = UniversityStatus.OPEN;
    final University university = new University();
    university.setId(1L);
    university.setName("TEST");
    final List<University> requestedUniversities = new ArrayList<>();
    requestedUniversities.add(university);
    final Map<UniversityStatus, List<University>> requestData = new HashMap<>();
    requestData.put(status, requestedUniversities);
    request.setData(requestData);
    final List<UniversityDomainModel> mappedModels = new ArrayList<>();
    final UniversityDomainModel mapperReturnedModel = new UniversityDomainModel();
    mapperReturnedModel.setId(1L);
    mapperReturnedModel.setName("TEST");
    mappedModels.add(mapperReturnedModel);
    final List<UniversityDomainModel> serviceReturned = new ArrayList<>();
    final UniversityDomainModel serviceReturnedModel = new UniversityDomainModel();
    serviceReturnedModel.setId(1L);
    serviceReturnedModel.setStatus(status);
    serviceReturnedModel.setName("TEST");
    serviceReturned.add(serviceReturnedModel);
    final Map<String, UniversityStatus> expectedResponseData = new HashMap<>();
    expectedResponseData.put("TEST", status);
    final int exceptedWarningsCount = 0;

    // When
    when(mapper.map(university, UniversityDomainModel.class)).thenReturn(mapperReturnedModel);
    when(universityService.setUniversitiesStatus(status, mappedModels)).thenReturn(serviceReturned);
    final SetUniversityStatusResponse actualResponse = universityController.setUniversityStatus(request);

    // Then
    assertThat(actualResponse.getData(), is(expectedResponseData));
    assertThat(actualResponse.getWarnings().size(), is(exceptedWarningsCount));
  }
}