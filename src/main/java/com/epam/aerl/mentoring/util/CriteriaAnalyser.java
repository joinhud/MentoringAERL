package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.*;
import com.epam.aerl.mentoring.exception.NotCombinedParameterException;
import com.epam.aerl.mentoring.exception.StudentClassCriteriaException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaAnalyser {
    private static final String S_CLASS = "S";
    private static final String RAND_CLASS = "rand";
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
            result = new LinkedHashMap<>();

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
        final int totalCount = criteria.get(S_CLASS);
        int sumCriteria = 0;

        for (Map.Entry<String, Integer> criteriaElement : criteria.entrySet()) {
            if (!criteriaElement.getKey().equals(S_CLASS)) {
                sumCriteria += criteriaElement.getValue();
            }
        }

        return totalCount - sumCriteria >= 0;
    }

    private void addRandClassCriteria(final Map<String, Integer> criteria) {
        final int totalCount = criteria.get(S_CLASS);
        int sumCriteria = 0;

        for (Map.Entry<String, Integer> criteriaElement : criteria.entrySet()) {
            if (!criteriaElement.getKey().equals(S_CLASS)) {
                sumCriteria += criteriaElement.getValue();
            }
        }

        criteria.put(RAND_CLASS, totalCount - sumCriteria);
    }

    private Map<String, Integer> combinedPossibleCriteria(final Map<String, Integer> criteria) {
        Map<String, StudentClassCriteria> studentClassCriteria = StudentClassCriteriaHolder.getInstance().receiveStudentClassCriteria();
        Map<String, Integer> result = new LinkedHashMap<>(criteria);

        for (Map.Entry<String, Integer> criteriaElement : criteria.entrySet()) {
            if (!criteriaElement.getKey().equals(S_CLASS)) {
                Map.Entry<String, Integer> appropriate = null;
                StudentClassCriteria combinedClassCriteria = null;

                for (Map.Entry<String, Integer> resultElement : result.entrySet()) {
                    if(!S_CLASS.equals(resultElement.getKey()) && !resultElement.getKey().contains(criteriaElement.getKey())) {
                        StudentClassCriteria first = studentClassCriteria.get(criteriaElement.getKey());
                        StudentClassCriteria second = studentClassCriteria.get(resultElement.getKey());

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
                        studentClassCriteria.put(combinedClassCriteriaName, combinedClassCriteria);

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
        StudentClassCriteria result = new StudentClassCriteria
                .StudentClassCriteriaBuilder()
                .ageCriteria(combineAges(first.getAgeCriteria(), second.getAgeCriteria()))
                .courseCriteria(combineCourses(first.getCourseCriteria(), second.getCourseCriteria()))
                .studentMarksWrapperCriteria(
                        combineMarksWrappers(first.getStudentMarksWrapperCriteria(), second.getStudentMarksWrapperCriteria())
                ).createClassCriteria();

        return result;
    }

    private StudentAgeCriteria combineAges(final StudentAgeCriteria first, final StudentAgeCriteria second)
            throws NotCombinedParameterException {
        StudentAgeCriteria result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                double[] combinedAges = combineRanges(
                        first.getMinAge(),
                        first.getMaxAge(),
                        second.getMinAge(),
                        second.getMaxAge());

                if (combinedAges != null) {
                    result = new StudentAgeCriteria
                            .StudentAgeCriteriaBuilder((int)combinedAges[MIN_INDEX], (int)combinedAges[MAX_INDEX])
                            .createAgeCriteria();
                } else {
                    throw new NotCombinedParameterException(NOT_COMBINED_PARAMETER_ERR_MSG);
                }
            }
        }

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
                result = new StudentMarksWrapper
                        .StudentMarksWrapperBuilder()
                        .marksCriteria(combineMarksCriteria(first.getMarksCriteria(), second.getMarksCriteria()))
                        .groupOperationsCriteria(combineGroupOperations(first.getGroupOperationsCriteria(), second.getGroupOperationsCriteria()))
                        .createMarksWrapper();
            }
        }

        return result;
    }

    private Map<Subject, StudentMarkCriteria> combineMarksCriteria(final Map<Subject, StudentMarkCriteria> first, final Map<Subject, StudentMarkCriteria> second)
            throws NotCombinedParameterException {
        Map<Subject, StudentMarkCriteria> result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                Map<Subject, StudentMarkCriteria> search;

                if (first.size() <= second.size()) {
                    result = second;
                    search = first;
                } else {
                    result = first;
                    search = second;
                }

                for (Map.Entry<Subject, StudentMarkCriteria> markCriteria : search.entrySet()) {
                    if (result.containsKey(markCriteria.getKey())) {
                        Subject subject = markCriteria.getKey();
                        result.put(subject, combineMarks(result.get(subject), markCriteria.getValue()));
                    } else {
                        result.put(markCriteria.getKey(), markCriteria.getValue());
                    }
                }
            }
        }

        return result;
    }

    private Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> combineGroupOperations(
            final Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> first,
            final Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> second) throws NotCombinedParameterException {
        Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                Map<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> search;

                if (first.size() <= second.size()) {
                    result = second;
                    search = first;
                } else {
                    result = first;
                    search = second;
                }

                for (Map.Entry<StudentMarksWrapper.GroupOperation, StudentMarkCriteria> markCriteria : search.entrySet()) {
                    if (result.containsKey(markCriteria.getKey())) {
                        StudentMarksWrapper.GroupOperation groupOperation = markCriteria.getKey();
                        result.put(groupOperation, combineMarks(result.get(groupOperation), markCriteria.getValue()));
                    } else {
                        result.put(markCriteria.getKey(), markCriteria.getValue());
                    }
                }
            }
        }

        return result;
    }

    private StudentCourseCriteria combineCourses(final StudentCourseCriteria first, final StudentCourseCriteria second)
            throws NotCombinedParameterException {
        StudentCourseCriteria result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                double[] combinedAges = combineRanges(
                        first.getMinCourse(),
                        first.getMaxCourse(),
                        second.getMinCourse(),
                        second.getMaxCourse());

                if (combinedAges != null) {
                    result = new StudentCourseCriteria
                            .StudentCourseCriteriaBuilder((int)combinedAges[MIN_INDEX], (int)combinedAges[MAX_INDEX])
                            .createCourseCriteria();
                } else {
                    throw new NotCombinedParameterException(NOT_COMBINED_PARAMETER_ERR_MSG);
                }
            }
        }

        return result;
    }

    private StudentMarkCriteria combineMarks(final StudentMarkCriteria first, final StudentMarkCriteria second)
            throws NotCombinedParameterException {
        StudentMarkCriteria result = null;

        if (first != null || second != null) {
            if (first == null) {
                result = second;
            } else if (second == null) {
                result = first;
            } else {
                double[] combinedAges = combineRanges(
                        first.getMinMark(),
                        first.getMaxMark(),
                        second.getMinMark(),
                        second.getMaxMark());

                if (combinedAges != null) {
                    result = new StudentMarkCriteria
                            .StudentMarkCriteriaBuilder(combinedAges[MIN_INDEX], combinedAges[MAX_INDEX])
                            .createMarkCriteria();
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
            min = firstMin > secondMin ? firstMin : secondMin;
            max = firstMax < secondMax ? firstMax : secondMax;
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
