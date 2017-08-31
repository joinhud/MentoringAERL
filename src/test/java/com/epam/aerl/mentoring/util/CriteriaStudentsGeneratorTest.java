package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentMarksWrapper;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.epam.aerl.mentoring.entity.StudentClassCriteria.createClassCriteriaBuilder;
import static com.epam.aerl.mentoring.entity.StudentMarksWrapper.createMarksWrapperBuilder;
import static com.epam.aerl.mentoring.entity.StudentRangeCriteria.createRangeCriteriaBuilder;

@RunWith(MockitoJUnitRunner.class)
public class CriteriaStudentsGeneratorTest {
    @Mock
    private StudentParametersGenerator generatorMock;
    @Mock
    private StudentClassCriteriaHolder holderMock;

    @InjectMocks
    private CriteriaStudentsGenerator criteriaGenerator = new CriteriaStudentsGenerator();

    @Test
    public void generateStudentsNullParameterTest() throws Exception {
        Map<String, Integer> criteria = null;

        List<Student> actual = criteriaGenerator.generateStudents(criteria);

        Assert.assertNull(actual);
        Mockito.verify(holderMock, Mockito.never()).getStudentClassCriteria();
    }

    @Test
    public void generateStudentsEmptyCriteriaMapTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();

        List<Student> students = criteriaGenerator.generateStudents(criteria);
        int actual = students.size();
        int expected = 0;

        Assert.assertEquals(expected, actual);
        Mockito.verify(holderMock, Mockito.never()).getStudentClassCriteria();
    }

    @Test
    public void generateStudentsNullCriteriaCountTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("S", null);

        List<Student> students = criteriaGenerator.generateStudents(criteria);
        int actual = students.size();
        int expected = 0;

        Assert.assertEquals(expected, actual);
        Mockito.verify(holderMock, Mockito.never()).getStudentClassCriteria();
    }

    @Test
    public void generateStudentsNullCriteriaNameTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put(null, 50);

        List<Student> students = criteriaGenerator.generateStudents(criteria);
        int actual = students.size();
        int expected = 0;

        Assert.assertEquals(expected, actual);
        Mockito.verify(holderMock, Mockito.never()).getStudentClassCriteria();
    }

    @Test
    public void generateStudentsOnlySCriteriaNameTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("S", 50);

        List<Student> students = criteriaGenerator.generateStudents(criteria);
        int actual = students.size();
        int expected = 0;

        Assert.assertEquals(expected, actual);
        Mockito.verify(holderMock, Mockito.never()).getStudentClassCriteria();
    }

    @Test
    public void generateStudentsCorrectCriteriaTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("S", 10);
        criteria.put("M", 5);
        criteria.put("rand", 5);

        Map<String, StudentClassCriteria> returned = new HashMap<>();
        returned.put("M", createClassCriteriaBuilder().build());
        returned.put("rand", createClassCriteriaBuilder().build());

        Mockito.when(holderMock.getStudentClassCriteria()).thenReturn(returned);
        List<Student> students = criteriaGenerator.generateStudents(criteria);

        int actual = students.size();
        int expected = 10;

        Assert.assertNotNull(students);
        Assert.assertEquals(expected, actual);
        Mockito.verify(holderMock, Mockito.atLeast(10)).getStudentClassCriteria();
        Mockito.verifyNoMoreInteractions(holderMock);
    }

    @Test
    public void generateNullParameterTest() throws Exception {
        StudentClassCriteria criteria = null;

        Student actual = criteriaGenerator.generate(criteria);

        Assert.assertNull(actual);
    }

    @Test
    public void generateRandomCriteriaTest() throws Exception {
        StudentClassCriteria criteria = createClassCriteriaBuilder().build();
        Integer returnedCourse = 3;
        Integer returnedAge = 21;
        Map<Subject, Integer> returnedMarks = new HashMap<>();
        returnedMarks.put(Subject.MATH, 6);
        returnedMarks.put(Subject.PHILOSOPHY, 6);
        returnedMarks.put(Subject.PHYSICAL_EDUCATION, 6);
        Student expected = new Student(returnedAge, returnedCourse, returnedMarks);

        Mockito.when(generatorMock.generateRandomStudentCourse()).thenReturn(returnedCourse);
        Mockito.when(generatorMock.generateStudentAgeByCourse(returnedCourse)).thenReturn(returnedAge);
        Mockito.when(generatorMock.generateRandomStudentMark()).thenReturn(6);
        Student actual  = criteriaGenerator.generate(criteria);

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
        Mockito.verify(generatorMock).generateRandomStudentCourse();
        Mockito.verify(generatorMock).generateStudentAgeByCourse(returnedCourse);
        Mockito.verify(generatorMock, Mockito.atLeast(3)).generateRandomStudentMark();
        Mockito.verifyNoMoreInteractions(generatorMock);
    }

    @Test
    public void generateRandomAgeCriteriaTest() throws Exception {
        int minCourse = 3;
        int maxCourse = 5;
        int minMathMark = 5;
        int maxMathMark = 9;

        Map<Subject, StudentRangeCriteria> marksCriteria = new HashMap<>();
        marksCriteria.put(Subject.MATH, createRangeCriteriaBuilder().min(minMathMark).max(maxMathMark).build());

        StudentClassCriteria criteria = createClassCriteriaBuilder()
                .courseCriteria(
                        createRangeCriteriaBuilder()
                                .min(minCourse)
                                .max(maxCourse)
                                .build()
                ).studentMarksWrapperCriteria(
                        createMarksWrapperBuilder().marksCriteria(marksCriteria).build()
                ).build();

        Integer returnedCourse = 4;
        Integer returnedAge = 22;
        Map<Subject, Integer> returnedMarks = new HashMap<>();
        returnedMarks.put(Subject.MATH, 7);
        returnedMarks.put(Subject.PHILOSOPHY, 6);
        returnedMarks.put(Subject.PHYSICAL_EDUCATION, 6);
        Student expected = new Student(returnedAge, returnedCourse, returnedMarks);

        Mockito.when(generatorMock.generateStudentCourse(minCourse, maxCourse)).thenReturn(returnedCourse);
        Mockito.when(generatorMock.generateStudentAgeByCourse(returnedCourse)).thenReturn(returnedAge);
        Mockito.when(generatorMock.generateRandomStudentMark()).thenReturn(6);
        Mockito.when(generatorMock.generateStudentMark(minMathMark, maxMathMark)).thenReturn(7);
        Student actual  = criteriaGenerator.generate(criteria);

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
        Mockito.verify(generatorMock).generateStudentCourse(minCourse, maxCourse);
        Mockito.verify(generatorMock).generateStudentAgeByCourse(returnedCourse);
        Mockito.verify(generatorMock).generateStudentMark(minMathMark, maxMathMark);
        Mockito.verify(generatorMock, Mockito.atLeast(2)).generateRandomStudentMark();
        Mockito.verifyNoMoreInteractions(generatorMock);
    }

    @Test
    public void generateRandomMarksCriteriaTest() throws Exception {
        int minAge = 21;
        int maxAge = 23;
        int minCourse = 2;
        int maxCourse = 5;

        StudentClassCriteria criteria = createClassCriteriaBuilder()
                .ageCriteria(
                        createRangeCriteriaBuilder().min(minAge).max(maxAge).build()
                ).courseCriteria(
                        createRangeCriteriaBuilder().min(minCourse).max(maxCourse).build()
                ).build();

        Integer returnedCourse = 3;
        Integer returnedAge = 22;
        Map<Subject, Integer> returnedMarks = new HashMap<>();
        returnedMarks.put(Subject.MATH, 2);
        returnedMarks.put(Subject.PHILOSOPHY, 2);
        returnedMarks.put(Subject.PHYSICAL_EDUCATION, 2);
        Student expected = new Student(returnedAge, returnedCourse, returnedMarks);

        Mockito.when(generatorMock.generateStudentCourse(minCourse, maxCourse)).thenReturn(returnedCourse);
        Mockito.when(generatorMock.generateStudentAgeByCourseAndRange(returnedCourse, minAge, maxAge)).thenReturn(returnedAge);
        Mockito.when(generatorMock.generateRandomStudentMark()).thenReturn(2);
        Student actual  = criteriaGenerator.generate(criteria);

        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
        Mockito.verify(generatorMock).generateStudentCourse(minCourse, maxCourse);
        Mockito.verify(generatorMock).generateStudentAgeByCourseAndRange(returnedCourse, minAge, maxAge);
        Mockito.verify(generatorMock, Mockito.atLeast(3)).generateRandomStudentMark();
        Mockito.verifyNoMoreInteractions(generatorMock);
    }
}