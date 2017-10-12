package com.epam.aerl.mentoring.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.epam.aerl.mentoring.entity.FindStudentsResponse;
import com.epam.aerl.mentoring.entity.GenerateStudentsRequest;
import com.epam.aerl.mentoring.entity.GenerateStudentsResponse;
import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.UpdateStudentsRequest;
import com.epam.aerl.mentoring.entity.UpdateStudentsResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class StudentControllerIntegrationTest {
  private static final String BASE_URL = "http://localhost:8080/MentoringAERL-CONTROLLER-1.0-SNAPSHOT/students/";

  private RestTemplate restTemplate;

  @Before
  public void setUp() throws Exception {
    restTemplate = new RestTemplate();
  }

  @Test(expected = HttpClientErrorException.class)
  public void testGenerateStudentByCriteria_IncorrectNumberOfStudents() {
    // Given
    final GenerateStudentsRequest request = new GenerateStudentsRequest();
    request.setData(31);

    // When
    restTemplate.postForEntity(BASE_URL, request, GenerateStudentsResponse.class);

    // Then
    fail("should not reach this point");
  }

  @Test
  public void testGenerateStudentByCriteria_CorrectNumberOfStudents() {
    // Given
    final int generationStudentsCount = 2;
    final int expectedResponseDataCount = 2;
    final GenerateStudentsRequest request = new GenerateStudentsRequest();
    request.setData(generationStudentsCount);

    // When
    final ResponseEntity<GenerateStudentsResponse> actualResponseEntity = restTemplate.postForEntity(BASE_URL, request, GenerateStudentsResponse.class);

    // Then
    assertNotNull(actualResponseEntity);
    assertThat(actualResponseEntity.getBody().getData().size(), is(expectedResponseDataCount));
    assertTrue(actualResponseEntity.getBody().getErrors().isEmpty());
    assertTrue(actualResponseEntity.getBody().getWarnings().isEmpty());
  }

  @Test
  public void testFindStudentsById_NotExistingIds() {
    // Given
    final String notExistingIdsStringPathParameter = "-100,1000";
    final int expectedResponseErrorsCount = 1;

    // When
    final FindStudentsResponse actualResponse = restTemplate.getForObject(BASE_URL + notExistingIdsStringPathParameter, FindStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertNull(actualResponse.getData());
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
    assertTrue(actualResponse.getWarnings().isEmpty());
  }

  @Test
  public void testFindStudentsById_PartiallyExistingIds() {
    // Given
    final String notExistingIdsStringPathParameter = "16,1000";
    final int expectedResponseWarningsCount = 1;
    final int expectedResponseDataCount = 1;

    // When
    final FindStudentsResponse actualResponse = restTemplate.getForObject(BASE_URL + notExistingIdsStringPathParameter, FindStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertThat(actualResponse.getData().size(), is(expectedResponseDataCount));
    assertTrue(actualResponse.getErrors().isEmpty());
    assertThat(actualResponse.getWarnings().size(), is(expectedResponseWarningsCount));
  }

  @Test
  public void testFindStudentsById_AllExistingIds() {
    // Given
    final String notExistingIdsStringPathParameter = "16,5";
    final int expectedResponseDataCount = 2;

    // When
    final FindStudentsResponse actualResponse = restTemplate.getForObject(BASE_URL + notExistingIdsStringPathParameter, FindStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertThat(actualResponse.getData().size(), is(expectedResponseDataCount));
    assertTrue(actualResponse.getErrors().isEmpty());
    assertTrue(actualResponse.getWarnings().isEmpty());
  }

  @Test
  public void testUpdateStudents_NullRequestBody() {
    // Given
    final List<Student> dummyRequestDta = null;
    final UpdateStudentsRequest request = new UpdateStudentsRequest();
    request.setData(dummyRequestDta);
    final HttpEntity<UpdateStudentsRequest> requestHttpEntity = new HttpEntity<>(request);
    final int expectedResponseErrorsCount = 1;

    // When
    final ResponseEntity<UpdateStudentsResponse> actualResponse = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestHttpEntity, UpdateStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertNull(actualResponse.getBody().getData());
    assertThat(actualResponse.getBody().getErrors().size(), is(expectedResponseErrorsCount));
    assertTrue(actualResponse.getBody().getWarnings().isEmpty());
  }

  @Test
  public void testUpdateStudents_NotExistingStudents() {
    // Given
    final Student notExistingStudent = new Student();
    notExistingStudent.setId(-100L);
    final List<Student> requestData = new ArrayList<>();
    requestData.add(notExistingStudent);
    final UpdateStudentsRequest request = new UpdateStudentsRequest();
    request.setData(requestData);
    final HttpEntity<UpdateStudentsRequest> requestHttpEntity = new HttpEntity<>(request);
    final int expectedResponseErrorsCount = 1;

    // When
    final ResponseEntity<UpdateStudentsResponse> actualResponse = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestHttpEntity, UpdateStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertNull(actualResponse.getBody().getData());
    assertThat(actualResponse.getBody().getErrors().size(), is(expectedResponseErrorsCount));
    assertTrue(actualResponse.getBody().getWarnings().isEmpty());
  }

  @Test
  public void testUpdateStudents_PartiallyExistingStudents() {
    // Given
    final Student notExistingStudent = new Student();
    notExistingStudent.setId(-100L);
    final Student existingStudent = new Student();
    existingStudent.setId(16L);
    existingStudent.setName("Jake");
    final List<Student> requestData = new ArrayList<>();
    requestData.add(notExistingStudent);
    requestData.add(existingStudent);
    final UpdateStudentsRequest request = new UpdateStudentsRequest();
    request.setData(requestData);
    final HttpEntity<UpdateStudentsRequest> requestHttpEntity = new HttpEntity<>(request);
    final int expectedResponseWarningsCount = 1;
    final int expectedResponseDataStudentsCount = 1;

    // When
    final ResponseEntity<UpdateStudentsResponse> actualResponse = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestHttpEntity, UpdateStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertThat(actualResponse.getBody().getData().size(), is(expectedResponseDataStudentsCount));
    assertTrue(actualResponse.getBody().getErrors().isEmpty());
    assertThat(actualResponse.getBody().getWarnings().size(), is(expectedResponseWarningsCount));
  }

  @Test
  public void testUpdateStudents_ExistingStudents() {
    // Given
    final Student existingStudent = new Student();
    existingStudent.setId(16L);
    existingStudent.setName("Mike");
    final List<Student> requestData = new ArrayList<>();
    requestData.add(existingStudent);
    final UpdateStudentsRequest request = new UpdateStudentsRequest();
    request.setData(requestData);
    final HttpEntity<UpdateStudentsRequest> requestHttpEntity = new HttpEntity<>(request);
    final int expectedResponseDataStudentsCount = 1;

    // When
    final ResponseEntity<UpdateStudentsResponse> actualResponse = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestHttpEntity, UpdateStudentsResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertThat(actualResponse.getBody().getData().size(), is(expectedResponseDataStudentsCount));
    assertTrue(actualResponse.getBody().getWarnings().isEmpty());
    assertTrue(actualResponse.getBody().getErrors().isEmpty());
  }
}