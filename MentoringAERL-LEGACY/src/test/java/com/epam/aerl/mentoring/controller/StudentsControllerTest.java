package com.epam.aerl.mentoring.controller;

import com.epam.aerl.mentoring.service.SerializableStudentsService;
import com.epam.aerl.mentoring.service.StudentsService;
import com.epam.aerl.mentoring.util.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentsControllerTest {

    @Mock
    private StudentsGenerator criteriaStudentsGeneratorMock;

    @Mock
    private StudentsService studentsServiceMock;

    @Mock
    private Printer printerMock;

    @Mock
    private CriteriaAnalyser analyserMock;

    @Mock
    private SerializableStudentsService serializableStudentsServiceMock;

    @InjectMocks
    private StudentsController controller;

    @Before
    public void setUp() throws Exception {
        controller = new StudentsController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTakeStudents_NullCriteriaString() {
        // Given
        final String nullCriteria = null;

        // When
        when(analyserMock.parse(nullCriteria)).thenReturn(null);
        controller.takeStudents(nullCriteria);

        // Then
        verify(analyserMock).parse(nullCriteria);
    }
}