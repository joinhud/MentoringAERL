package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.type.StudentClass;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaAnalyser {
    private static final String VALUE_REGEX = "\\d+";
    private static final String CLASS_REGEX = "[SMYPLBONI]";

    public Map<StudentClass, Integer> parse(final String criteria) {
        Map<StudentClass, Integer> result = null;

        if (criteria != null) {
            result = new LinkedHashMap<>();

            final Pattern pattern = Pattern.compile(VALUE_REGEX + CLASS_REGEX);
            final Matcher matcher = pattern.matcher(criteria);

            while (matcher.find()) {
                final String classCriteria = matcher.group();
                final StudentClass studentClass = parseStudentClass(classCriteria);
                final Integer studentClassValue = parseStudentClassValue(classCriteria);

                if (studentClass != null && studentClassValue != null) {
                    result.put(studentClass, studentClassValue);
                }
            }
        }

        return result;
    }

    private StudentClass parseStudentClass(final String criteria) {
        StudentClass result = null;

        final Matcher matcher = Pattern.compile(CLASS_REGEX).matcher(criteria);

        if (matcher.find()) {
            result = StudentClass.valueOf(matcher.group());
        }

        return result;
    }

    private Integer parseStudentClassValue(final String criteria) {
        Integer result = null;

        final Matcher matcher = Pattern.compile(VALUE_REGEX).matcher(criteria);

        if (matcher.find()) {
            result = Integer.valueOf(matcher.group());
        }

        return result;
    }
}
