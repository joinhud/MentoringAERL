package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentMarksWrapper;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.exception.NotCombinedParameterException;
import com.epam.aerl.mentoring.exception.StudentClassCriteriaException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.GenerationClass;
import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaAnalyser {
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 1;
    private static final String NOT_COMBINED_PARAMETER_ERR_MSG = "Parameters cannot be combined.";
    private static final String NOT_COMBINED_HAS_CONFLICT_ERR_MSG = "Combined criteria has conflicts.";
    private static final String VALUE_REGEX = "\\d+";
    private static final String CLASS_REGEX = "[SMYPLBONI]";
    private static final Logger LOG = LogManager.getLogger(CriteriaAnalyser.class);

    public Map<String, Integer> parse(final String criteria) {
        Map<String, Integer> result = null;

        if (criteria != null) {
            result = new HashMap<>();

            final Pattern pattern = Pattern.compile(VALUE_REGEX + CLASS_REGEX);
            final Matcher matcher = pattern.matcher(criteria);

            while (matcher.find()) {
                final String classCriteria = matcher.group();
                final String studentClass = parseStudentClass(classCriteria);
                final Integer studentClassValue = parseStudentClassValue(classCriteria);

                if (studentClass != null && studentClassValue != null) {
                    result.put(studentClass, studentClassValue);
                }
            }
        }

        return result;
    }

    public Map<String, Integer> validate(final Map<String, Integer> parsedCriteria) throws StudentClassCriteriaException {
        Map<String, Integer> result = null;

        if (parsedCriteria != null) {
            if (validateCriteriaValues(parsedCriteria)) {
                result = parsedCriteria;
                addRandClassCriteria(result);
            } else {
                Map<String, Integer> combined = combinedPossibleCriteria(parsedCriteria);

                if (validateCriteriaValues(combined)) {
                    result = combined;
                    addRandClassCriteria(result);
                } else {
                    throw new StudentClassCriteriaException(NOT_COMBINED_HAS_CONFLICT_ERR_MSG, ErrorMessage.CONFLICTS_INPUT_CRITERIA.getCode());
                }
            }
        }

        return result;
    }

    private boolean validateCriteriaValues(final Map<String, Integer> criteria) {
        final int totalCount = criteria.get(GenerationClass.S.toString());
        int sumCriteria = 0;

        for (Entry<String, Integer> criteriaElement : criteria.entrySet()) {
            if (!criteriaElement.getKey().equals(GenerationClass.S.toString())) {
                sumCriteria += criteriaElement.getValue();
            }
        }

        return totalCount - sumCriteria >= 0;
    }

    private void addRandClassCriteria(final Map<String, Integer> criteria) {
        final int totalCount = criteria.get(GenerationClass.S.toString());
        int sumCriteria = 0;

        for (Entry<String, Integer> criteriaElement : criteria.entrySet()) {
            if (!criteriaElement.getKey().equals(GenerationClass.S.toString())) {
                sumCriteria += criteriaElement.getValue();
            }
        }

        criteria.put(GenerationClass.RAND.toString().toLowerCase(), totalCount - sumCriteria);
    }

    private Map<String, Integer> combinedPossibleCriteria(final Map<String, Integer> criteria) {
        StudentClassCriteriaHolder holder = StudentClassCriteriaHolder.getInstance();
        Map<String, Integer> result = new HashMap<>(criteria);

        for (Entry<String, Integer> criteriaElement : criteria.entrySet()) {
            if (!criteriaElement.getKey().equals(GenerationClass.S.toString())) {
                Entry<String, Integer> appropriate = null;
                StudentClassCriteria combinedClassCriteria = null;

                for (Entry<String, Integer> resultElement : result.entrySet()) {
                    if(!GenerationClass.S.toString().equals(resultElement.getKey()) && !resultElement.getKey().contains(criteriaElement.getKey())) {
                        StudentClassCriteria first = holder.getCriteriaByGenerationClass(criteriaElement.getKey());
                        StudentClassCriteria second = holder.getCriteriaByGenerationClass(resultElement.getKey());

                        try {
                            combinedClassCriteria = combineStudentClassesCriteria(first, second);

                            appropriate = resultElement;
                            break;
                        } catch (NotCombinedParameterException e) {
                            LOG.warn(e);
                        }
                    }
                }

                if (appropriate != null) {
                    Integer oldValue = result.get(criteriaElement.getKey());

                    if (oldValue != null && combinedClassCriteria != null) {
                        String combinedClassCriteriaName = criteriaElement.getKey() + appropriate.getKey();
                        holder.putCombinedStudentClassCriteria(combinedClassCriteriaName, combinedClassCriteria);

                        if (oldValue - appropriate.getValue() == 0) {
                            result.remove(criteriaElement.getKey());
                            result.remove(appropriate.getKey());
                            result.put(combinedClassCriteriaName, oldValue);
                        } else if (oldValue - appropriate.getValue() > 0) {
                            result.put(criteriaElement.getKey(), oldValue - appropriate.getValue());
                            result.remove(appropriate.getKey());
                            result.put(combinedClassCriteriaName, appropriate.getValue());
                        } else {
                            result.put(appropriate.getKey(), appropriate.getValue() - oldValue);
                            result.remove(criteriaElement.getKey());
                            result.put(combinedClassCriteriaName, oldValue);
                        }
                    }
                }
            }
        }

        return result;
    }

    private StudentClassCriteria combineStudentClassesCriteria(final StudentClassCriteria first, final StudentClassCriteria second)
            throws NotCombinedParameterException {
        StudentClassCriteria result = StudentClassCriteria.newBuilder()
                .ageCriteria(combineRangeCriteria(first.getAgeCriteria(), second.getAgeCriteria()))
                .courseCriteria(combineRangeCriteria(first.getCourseCriteria(), second.getCourseCriteria()))
                .studentMarksWrapperCriteria(
                        combineMarksWrappers(first.getStudentMarksWrapperCriteria(), second.getStudentMarksWrapperCriteria())
                ).build();

        return result;
    }

    private StudentMarksWrapper combineMarksWrappers(final StudentMarksWrapper first, final StudentMarksWrapper second) throws NotCombinedParameterException {
        StudentMarksWrapper result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                result = StudentMarksWrapper
                        .newBuilder()
                        .marksCriteria(
                                combineMapsCriteria(
                                        first.getMarksCriteria(),
                                        second.getMarksCriteria()
                                )
                        ).groupOperationsCriteria(
                                combineMapsCriteria(
                                        first.getGroupOperationsCriteria(),
                                        second.getGroupOperationsCriteria()
                                )
                        )
                        .build();
            }
        }

        return result;
    }

    private <T> Map<T, StudentRangeCriteria> combineMapsCriteria(
            final Map<T, StudentRangeCriteria> first,
            final Map<T, StudentRangeCriteria> second) throws NotCombinedParameterException {
        Map<T, StudentRangeCriteria> result = null;

        if (MapUtils.isNotEmpty(first) || MapUtils.isNotEmpty(second)) {
            if (MapUtils.isEmpty(first)) {
                result = second;
            } else if (MapUtils.isEmpty(second)) {
                result = first;
            } else {
                Map<T, StudentRangeCriteria> search = null;

                if (first.size() <= second.size()) {
                    result = second;
                    search = first;
                } else {
                    result = first;
                    search = second;
                }

                for (Entry<T, StudentRangeCriteria> criteria : search.entrySet()) {
                    if (result.containsKey(criteria.getKey())) {
                        T key = criteria.getKey();
                        result.put(key, combineRangeCriteria(result.get(key), criteria.getValue()));
                    } else {
                        result.put(criteria.getKey(), criteria.getValue());
                    }
                }
            }
        }

        return result;
    }

    private StudentRangeCriteria combineRangeCriteria(final StudentRangeCriteria first, final StudentRangeCriteria second)
            throws NotCombinedParameterException {
        StudentRangeCriteria result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                double[] combined = combineRanges(
                        first.getMin(),
                        first.getMax(),
                        second.getMin(),
                        second.getMax());

                if (combined != null) {
                    result = StudentRangeCriteria
                            .newBuilder()
                            .min(combined[MIN_INDEX])
                            .max(combined[MAX_INDEX])
                            .build();
                } else {
                    throw new NotCombinedParameterException(NOT_COMBINED_PARAMETER_ERR_MSG);
                }
            }
        }

        return result;
    }

    private double[] combineRanges(final double firstMin, final double firstMax, final double secondMin, final double secondMax) {
        double[] result = null;

        Double min = null;
        Double max = null;

        if (firstMin <= secondMax && firstMax >= secondMin) {
            min = Math.max(firstMin, secondMin);
            max = Math.min(firstMax, secondMax);
        }

        if (min != null) {
            result = new double[] {min, max};
        }

        return result;
    }

    private String parseStudentClass(final String criteria) {
        String result = null;

        final Matcher matcher = Pattern.compile(CLASS_REGEX).matcher(criteria);

        if (matcher.find()) {
            result = matcher.group();
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
