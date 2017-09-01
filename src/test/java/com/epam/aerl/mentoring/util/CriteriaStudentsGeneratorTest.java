package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.type.Subject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.aerl.mentoring.entity.StudentClassCriteria.createClassCriteriaBuilder;
import static com.epam.aerl.mentoring.entity.StudentMarksWrapper.createMarksWrapperBuilder;
import static com.epam.aerl.mentoring.entity.StudentRangeCriteria.createRangeCriteriaBuilder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class CriteriaStudentsGeneratorTest {

    @Mock
    private StudentParametersGenerator generatorMock;

    @Mock
    private StudentClassCriteriaHolder holderMock;

    @InjectMocks
    private CriteriaStudentsGenerator criteriaGenerator = null;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        criteriaGenerator = new CriteriaStudentsGenerator();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateStudents_NullCriteriaParameter() {
        // Given
        final Map<String, Integer> nullCriteria = null;
        final List<Student> expectedResult = null;

        // When
        final List<Student> actualResult = criteriaGenerator.generateStudents(nullCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(holderMock, never()).getStudentClassCriteria();
    }

    @Test
    public void testGenerateStudents_EmptyCriteriaMap() {
        // Given
        final List<Student> expectedResult = new ArrayList<>();

        // When
        final List<Student> actualResult = criteriaGenerator.generateStudents(criteriaMap(new HashMap<>()));

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(holderMock, never()).getStudentClassCriteria();
    }

    @Test
    public void testGenerateStudents_NullCriteriaCount() {
        // Given
        final Map<String, Integer> nullCriteriaCount = new HashMap<>();
        nullCriteriaCount.put(criteriaName("S"), countOfGeneration(null));
        final List<Student> expectedResult = new ArrayList<>();

        // When
        final List<Student> actualResult = criteriaGenerator.generateStudents(nullCriteriaCount);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(holderMock, never()).getStudentClassCriteria();
    }

    @Test
    public void testGenerateStudents_NullCriteriaName() {
        // Given
        final Map<String, Integer> criteriaWithNullCriteriaName = new HashMap<>();
        criteriaWithNullCriteriaName.put(criteriaName(null), countOfGeneration(50));
        final List<Student> expectedResult = new ArrayList<>();

        // When
        final List<Student> actualResult = criteriaGenerator.generateStudents(criteriaWithNullCriteriaName);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(holderMock, never()).getStudentClassCriteria();
    }

    @Test
    public void testGenerateStudents_OnlySCriteria() {
        // Given
        final Map<String, Integer> criteriaWithOnlyS = new HashMap<>();
        criteriaWithOnlyS.put(criteriaName("S"), countOfGeneration(50));
        final List<Student> expectedResult = new ArrayList<>();

        // When
        final List<Student> actualResult = criteriaGenerator.generateStudents(criteriaWithOnlyS);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(holderMock, never()).getStudentClassCriteria();
    }

    @Test
    public void testGenerateStudents_CorrectCriteria() {
        // Given
        final Map<String, Integer> correctCriteria = new HashMap<>();
        correctCriteria.put(criteriaName("S"), countOfGeneration(10));
        correctCriteria.put(criteriaName("M"), countOfGeneration(5));
        correctCriteria.put(criteriaName("rand"), countOfGeneration(5));

        final Map<String, StudentClassCriteria> returnedCriteria = new HashMap<>();
        returnedCriteria.put("M", createClassCriteriaBuilder().build());
        returnedCriteria.put("rand", createClassCriteriaBuilder().build());

        final int expectedResultSize = 10;

        // When
        when(holderMock.getStudentClassCriteria()).thenReturn(returnedCriteria);
        final int actualResultSize = criteriaGenerator.generateStudents(correctCriteria).size();

        // Then
        assertThat(actualResultSize, is(expectedResultSize));
        verify(holderMock, atLeast(10)).getStudentClassCriteria();
    }

    @Test
    public void testGenerate_NullCriteriaParameter() {
        // Given
        final StudentClassCriteria nullCriteria = null;
        final Student expectedResult = null;

        // When
        final Student actualResult = criteriaGenerator.generate(nullCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testGenerate_RandomCriteria() {
        // Given
        final StudentClassCriteria randomCriteria = createClassCriteriaBuilder().build();
        final Integer returnedCourse = 3;
        final Integer returnedAge = 21;
        final Integer returnedMark = 6;
        final Map<Subject, Integer> returnedMarks = new HashMap<>();
        returnedMarks.put(Subject.MATH, returnedMark);
        returnedMarks.put(Subject.PHILOSOPHY, returnedMark);
        returnedMarks.put(Subject.PHYSICAL_EDUCATION, returnedMark);
        final Student expectedResult = new Student(returnedAge, returnedCourse, returnedMarks);

        // When
        when(generatorMock.generateRandomStudentCourse()).thenReturn(returnedCourse);
        when(generatorMock.generateStudentAgeByCourse(returnedCourse)).thenReturn(returnedAge);
        when(generatorMock.generateRandomStudentMark()).thenReturn(returnedMark);
        Student actualResult = criteriaGenerator.generate(randomCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(generatorMock).generateRandomStudentCourse();
        verify(generatorMock).generateStudentAgeByCourse(returnedCourse);
        verify(generatorMock, atLeast(3)).generateRandomStudentMark();
    }

    @Test
    public void testGenerate_RandomAgeCriteria() {
        // Given
        final int minCourse = 3;
        final int maxCourse = 5;
        final int minMathMark = 5;
        final int maxMathMark = 9;
        final Map<Subject, StudentRangeCriteria> marksCriteria = new HashMap<>();
        marksCriteria.put(Subject.MATH, createRangeCriteriaBuilder().min(minMathMark).max(maxMathMark).build());

        final StudentClassCriteria criteria = createClassCriteriaBuilder()
                .courseCriteria(
                        createRangeCriteriaBuilder()
                                .min(minCourse)
                                .max(maxCourse)
                                .build()
                ).studentMarksWrapperCriteria(
                        createMarksWrapperBuilder().marksCriteria(marksCriteria).build()
                ).build();

        final Integer returnedCourse = 4;
        final Integer returnedAge = 22;
        final Integer returnedMathMark = 7;
        final Integer returnedRandomMark = 6;
        final Map<Subject, Integer> returnedMarks = new HashMap<>();
        returnedMarks.put(Subject.MATH, returnedMathMark);
        returnedMarks.put(Subject.PHILOSOPHY, returnedRandomMark);
        returnedMarks.put(Subject.PHYSICAL_EDUCATION, returnedRandomMark);
        final Student expectedResult = new Student(returnedAge, returnedCourse, returnedMarks);

        // When
        when(generatorMock.generateStudentCourse(minCourse, maxCourse)).thenReturn(returnedCourse);
        when(generatorMock.generateStudentAgeByCourse(returnedCourse)).thenReturn(returnedAge);
        when(generatorMock.generateRandomStudentMark()).thenReturn(returnedRandomMark);
        when(generatorMock.generateStudentMark(minMathMark, maxMathMark)).thenReturn(returnedMathMark);
        final Student actualResult  = criteriaGenerator.generate(criteria);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(generatorMock).generateStudentCourse(minCourse, maxCourse);
        verify(generatorMock).generateStudentAgeByCourse(returnedCourse);
        verify(generatorMock).generateStudentMark(minMathMark, maxMathMark);
        verify(generatorMock, atLeast(2)).generateRandomStudentMark();
    }

    @Test
    public void testGenerate_RandomMarksCriteria() {
        // Given
        final int minAge = 21;
        final int maxAge = 23;
        final int minCourse = 2;
        final int maxCourse = 5;
        final StudentClassCriteria criteria = createClassCriteriaBuilder()
                .ageCriteria(
                        createRangeCriteriaBuilder().min(minAge).max(maxAge).build()
                ).courseCriteria(
                        createRangeCriteriaBuilder().min(minCourse).max(maxCourse).build()
                ).build();

        final Integer returnedCourse = 3;
        final Integer returnedAge = 22;
        final Integer returnedMark = 2;
        final Map<Subject, Integer> returnedMarks = new HashMap<>();
        returnedMarks.put(Subject.MATH, returnedMark);
        returnedMarks.put(Subject.PHILOSOPHY, returnedMark);
        returnedMarks.put(Subject.PHYSICAL_EDUCATION, returnedMark);
        final Student expectedResult = new Student(returnedAge, returnedCourse, returnedMarks);

        // When
        when(generatorMock.generateStudentCourse(minCourse, maxCourse)).thenReturn(returnedCourse);
        when(generatorMock.generateStudentAgeByCourseAndRange(returnedCourse, minAge, maxAge)).thenReturn(returnedAge);
        when(generatorMock.generateRandomStudentMark()).thenReturn(returnedMark);
        final Student actualResult  = criteriaGenerator.generate(criteria);

        // Then
        assertThat(actualResult, is(expectedResult));
        verify(generatorMock).generateStudentCourse(minCourse, maxCourse);
        verify(generatorMock).generateStudentAgeByCourseAndRange(returnedCourse, minAge, maxAge);
        verify(generatorMock, atLeast(3)).generateRandomStudentMark();
    }

    private Map<String, Integer> criteriaMap(final Map<String, Integer> criteria) {
        return criteria;
    }

    private String criteriaName(final String criteriaName) {
        return criteriaName;
    }

    private Integer countOfGeneration(final Integer count) {
        return count;
    }
}