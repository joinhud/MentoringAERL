package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.exception.StudentClassCriteriaException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CriteriaAnalyserTest {
    private CriteriaAnalyser analyser = null;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        analyser = new CriteriaAnalyser();
    }

    @Test
    public void testParse_NullCriteria() {
        // Given
        final Map<String, Integer> expectedResult = null;
        final String nullInputString = null;

        // When
        final Map<String, Integer> actualResult = analyser.parse(nullInputString);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testParse_EmptyCriteria() {
        // Given
        final Map<String, Integer> expectedResult = new HashMap<>();

        // When
        final Map<String, Integer> actualResult = analyser.parse(inputString(new String()));

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testParse_InappropriateCriteria() {
        // Given
        final String inappropriateInputString = "-123-=12=32~12!212!NM2^@3#3$4%5^6&7*8(09-+_//8{[f]}f:f;f\'A2\\E2";
        final Map<String, Integer> expectedResult = new HashMap<>();

        // When
        final Map<String, Integer> actualResult = analyser.parse(inappropriateInputString);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testParse_PartiallyAppropriateParameter() {
        // Given
        final String partiallyAppropriateInputString = "asjdba%%%skdjbasd---++213217||| 45S2c ,,sa;";
        final Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put(criteriaName("S"), countOfGeneration(45));
        expectedResult.put(criteriaName("c"), countOfGeneration(2));

        // When
        final Map<String, Integer> actualResult = analyser.parse(partiallyAppropriateInputString);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testParse_AppropriateParameter() {
        // Given
        final String appropriateInputString = "78D90S67V9c45a77p";
        final Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put(criteriaName("D"), countOfGeneration(78));
        expectedResult.put(criteriaName("S"), countOfGeneration(90));
        expectedResult.put(criteriaName("V"), countOfGeneration(67));
        expectedResult.put(criteriaName("c"), countOfGeneration(9));
        expectedResult.put(criteriaName("a"), countOfGeneration(45));
        expectedResult.put(criteriaName("p"), countOfGeneration(77));

        // When
        final Map<String, Integer> actualResult = analyser.parse(appropriateInputString);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testSort_NullCriteria() {
        // Given
        final Map<String, Integer> nullCriteria = null;
        final String expectedResult = null;

        // When
        final String actualResult = analyser.sortCriteria(nullCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testSort_EmptyCriteria() {
        // Given
        final String expectedResult = null;

        // When
        final String actual = analyser.sortCriteria(criteria(new HashMap<>()));

        // Then
        assertThat(actual, is(expectedResult));
    }

    @Test
    public void testSort_CorrectCriteria() {
        // Given
        final String expectedResult = "23S55L34a34l";
        final Map<String, Integer> correctCriteria = new HashMap<>();
        correctCriteria.put(criteriaName("a"), countOfGeneration(34));
        correctCriteria.put(criteriaName("L"), countOfGeneration(55));
        correctCriteria.put(criteriaName("S"), countOfGeneration(23));
        correctCriteria.put(criteriaName("l"), countOfGeneration(34));

        // When
        final String actualResult = analyser.sortCriteria(correctCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testValidate_NullParameter() throws Exception {
        // Given
        final Map<String, Integer> nullCriteria = null;
        final Map<String, Integer> expectedResult = null;

        // When
        final Map<String, Integer> actualResult = analyser.validate(nullCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testValidate_InappropriateParameter() throws Exception {
        // Given
        final Map<String, Integer> inappropriateCriteria = new HashMap<>();
        inappropriateCriteria.put(criteriaName("123"), countOfGeneration(123));
        inappropriateCriteria.put(criteriaName("tt"), countOfGeneration(555));
        inappropriateCriteria.put(criteriaName("--"), countOfGeneration(null));

        // Expect
        exception.expect(StudentClassCriteriaException.class);

        // When
        analyser.validate(inappropriateCriteria);
    }

    @Test
    public void testValidate_CombinedCriteria() throws Exception {
        // Given
        final Map<String, Integer> combinedCriteria = new HashMap<>();
        combinedCriteria.put(criteriaName("S"), countOfGeneration(10));
        combinedCriteria.put(criteriaName("M"), countOfGeneration(10));
        combinedCriteria.put(criteriaName("P"), countOfGeneration(5));
        final Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put(criteriaName("rand"), countOfGeneration(0));
        expectedResult.put(criteriaName("S"), countOfGeneration(10));
        expectedResult.put(criteriaName("PM"), countOfGeneration(5));
        expectedResult.put(criteriaName("M"), countOfGeneration(5));

        // When
        final Map<String, Integer> actualResult = analyser.validate(combinedCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    @Test
    public void testValidate_NotCombinedCriteria() throws Exception {
        // Given
        final Map<String, Integer> notCombinedCriteria = new HashMap<>();
        notCombinedCriteria.put("S", 10);
        notCombinedCriteria.put("B", 6);
        notCombinedCriteria.put("Y", 6);

        // Expect
        exception.expect(StudentClassCriteriaException.class);

        // When
        analyser.validate(notCombinedCriteria);
    }

    @Test
    public void testValidate_NotRequiringCombination() throws Exception {
        // Given
        final Map<String, Integer> notRequiringCombinationCriteria = new HashMap<>();
        notRequiringCombinationCriteria.put("S", 10);
        notRequiringCombinationCriteria.put("B", 2);
        notRequiringCombinationCriteria.put("Y", 2);

        final Map<String, Integer> expectedResult = new HashMap<>(notRequiringCombinationCriteria);
        expectedResult.put("rand", 6);

        // When
        final Map<String, Integer> actualResult = analyser.validate(notRequiringCombinationCriteria);

        // Then
        assertThat(actualResult, is(expectedResult));
    }

    private String inputString(final String input) {
        return input;
    }

    private String criteriaName(final String criteriaName) {
        return criteriaName;
    }

    private Integer countOfGeneration(final Integer count) {
        return count;
    }

    private Map<String, Integer> criteria(final Map<String, Integer> criteria) {
        return criteria;
    }
}