package com.epam.aerl.mentoring.controller;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.epam.aerl.mentoring.entity.AddNewUniversityRequest;
import com.epam.aerl.mentoring.entity.AddNewUniversityResponse;
import com.epam.aerl.mentoring.entity.University;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

public class UniversityControllerIntegrationTest {
  private static final String BASE_URL = "http://localhost:8080/MentoringAERL-CONTROLLER-1.0-SNAPSHOT/universities/";

  private RestTemplate restTemplate;

  @Before
  public void setUp() throws Exception {
    restTemplate = new RestTemplate();
  }

  @Test
  public void testAddNewUniversity_NullRequestData() {
    // Given
    final University dummyUniversity = null;
    final AddNewUniversityRequest request = new AddNewUniversityRequest();
    request.setData(dummyUniversity);
    final int expectedResponseErrorsCount = 1;

    // When
    final AddNewUniversityResponse response = restTemplate.postForObject(BASE_URL, request, AddNewUniversityResponse.class);

    // Then
    assertNotNull(response);
    assertNull(response.getData());
    assertThat(response.getErrors().size(), is(expectedResponseErrorsCount));
    assertTrue(response.getWarnings().isEmpty());
  }

  @Test
  public void testAddNewUniversity_IncorrectRequestDataProperties() {
    // Given
    final University requestData = new University();
    requestData.setName("0123456789012345");
    requestData.setDescription("Test Description");
    requestData.setFoundationDate(LocalDate.of(1988, 11, 4));
    final AddNewUniversityRequest request = new AddNewUniversityRequest();
    request.setData(requestData);
    final int expectedResponseErrorsCount = 1;

    // When
    final AddNewUniversityResponse actualResponse = restTemplate.postForObject(BASE_URL, request, AddNewUniversityResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertNull(actualResponse.getData());
    assertThat(actualResponse.getErrors().size(), is(expectedResponseErrorsCount));
    assertTrue(actualResponse.getWarnings().isEmpty());
  }

  @Test
  public void testAddNewUniversity_CorrectRequestData() {
    // Given
    final University requestData = new University();
    requestData.setName("Test");
    requestData.setDescription("Test Description");
    requestData.setFoundationDate(LocalDate.of(1988, 11, 4));
    final AddNewUniversityRequest request = new AddNewUniversityRequest();
    request.setData(requestData);

    // When
    final AddNewUniversityResponse actualResponse = restTemplate.postForObject(BASE_URL, request, AddNewUniversityResponse.class);

    // Then
    assertNotNull(actualResponse);
    assertNotNull(actualResponse.getData());
    assertTrue(actualResponse.getErrors().isEmpty());
    assertTrue(actualResponse.getWarnings().isEmpty());
  }
}