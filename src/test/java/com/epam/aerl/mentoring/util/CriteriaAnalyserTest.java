package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.exception.StudentClassCriteriaException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CriteriaAnalyserTest {
    private CriteriaAnalyser analyser;

    @Before
    public void setUp() {
        analyser = new CriteriaAnalyser();
    }

    @Test
    public void parseNullParameterTest() {
        String param = null;
        Map<String, Integer> actual = analyser.parse(param);

        Assert.assertNull(actual);
    }

    @Test
    public void parseNotNullParameterTest() {
        String param = new String();
        Map<String, Integer> actual = analyser.parse(param);

        Assert.assertNotNull(actual);
    }

    @Test
    public void parseInappropriateParameterTest() {
        String param = "-123-=12=32~12!212!NM2^@3#3$4%5^6&7*8(09-+_//8{[f]}f:f;f\'A2\\E2";
        Map<String, Integer> result = analyser.parse(param);
        int actual = result.size();
        int expected = 0;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parsePartiallyAppropriateParameterTest() {
        String param = "asjdbaskdjbasd---++213217|||45S,,sa;2C";
        Map<String, Integer> result = analyser.parse(param);
        int actual = result.size();
        int expected = 2;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void parseAppropriateParameterTest() {
        String param = "78D90S67V9c45a77p";
        Map<String, Integer> result = analyser.parse(param);
        int actual = result.size();
        int expected = 6;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void sortCriteriaNullParameterTest() {
        Map<String, Integer> criteria = null;
        String actual = analyser.sortCriteria(criteria);

        Assert.assertNull(actual);
    }

    @Test
    public void sortCriteriaNotNullParameterTest() {
        Map<String, Integer> criteria = new HashMap<>();
        String actual = analyser.sortCriteria(criteria);

        Assert.assertNotNull(actual);
    }

    @Test
    public void sortCriteriaCorrectTest() {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("a", 34);
        criteria.put("L", 55);
        criteria.put("S", 23);
        criteria.put("l", 34);
        String expected = "23S55L34a34l";
        String actual = analyser.sortCriteria(criteria);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validateNullParameterTest() throws Exception {
        Map<String, Integer> criteria = null;
        Map<String, Integer> actual = analyser.validate(criteria);

        Assert.assertNull(actual);
    }

    @Test(expected = StudentClassCriteriaException.class)
    public void validateInappropriateParameterTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("123", 123);
        criteria.put("tt", 555);
        criteria.put("--", null);
        Map<String, Integer> actual = analyser.validate(criteria);

        Assert.assertNull(actual);
    }

    @Test
    public void validateCombinedCriteriaTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("S", 10);
        criteria.put("M", 10);
        criteria.put("P", 5);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("rand", 0);
        expected.put("S", 10);
        expected.put("PM", 5);
        expected.put("M", 5);

        Map<String, Integer> actual = analyser.validate(criteria);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = StudentClassCriteriaException.class)
    public void validateNotCombinedCriteriaTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("S", 10);
        criteria.put("B", 6);
        criteria.put("Y", 6);
        Map<String, Integer> actual = analyser.validate(criteria);

        Assert.assertNull(actual);
    }

    @Test
    public void validateNotRequiringCombinationTest() throws Exception {
        Map<String, Integer> criteria = new HashMap<>();
        criteria.put("S", 10);
        criteria.put("B", 2);
        criteria.put("Y", 2);
        Map<String, Integer> expected = new HashMap<>(criteria);
        expected.put("rand", 6);
        Map<String, Integer> actual = analyser.validate(criteria);

        Assert.assertEquals(expected, actual);
    }
}