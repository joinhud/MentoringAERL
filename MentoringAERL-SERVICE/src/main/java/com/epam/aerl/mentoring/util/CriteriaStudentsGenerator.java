package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentClassCriteria;
import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.entity.StudentMarksWrapper.GroupOperation;
import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.type.GenerationClass;
import com.epam.aerl.mentoring.type.Subject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("criteriaStudentsGenerator")
public class CriteriaStudentsGenerator implements StudentsGenerator {
  @Autowired
  @Qualifier("studentParametersGenerator")
  private StudentParametersGenerator studentParametersGenerator;

  @Autowired
  @Qualifier("studentClassCriteriaHolder")
  private StudentClassCriteriaHolder studentClassCriteriaHolder;

  @Autowired
  @Qualifier("studentPropertiesHolder")
  private StudentPropertiesHolder studentPropertiesHolder;

  public void setStudentParametersGenerator(final StudentParametersGenerator studentParametersGenerator) {
    this.studentParametersGenerator = studentParametersGenerator;
  }

  public void setStudentClassCriteriaHolder(final StudentClassCriteriaHolder studentClassCriteriaHolder) {
    this.studentClassCriteriaHolder = studentClassCriteriaHolder;
  }

  public void setStudentPropertiesHolder(final StudentPropertiesHolder studentPropertiesHolder) {
    this.studentPropertiesHolder = studentPropertiesHolder;
  }

  @Override
  public List<StudentDomainModel> generateStudents(final Map<String, Integer> criteria) {

    List<StudentDomainModel> students = null;

    if (MapUtils.isNotEmpty(criteria)) {
      students = new ArrayList<>();

      for (Map.Entry<String, Integer> parameter : criteria.entrySet()) {
        if (parameter.getKey() != null && !parameter.getKey().equals(GenerationClass.S.toString())) {
          Integer count = parameter.getValue();

          if (count != null) {
            for (int i = 0; i < count; i++) {
              StudentDomainModel student = generate(this.studentClassCriteriaHolder.getStudentClassCriteria().get(parameter.getKey()));
              students.add(student);
            }
          }
        }
      }
    }

    return students;
  }

  public StudentDomainModel generate(final StudentClassCriteria criteria) {
    StudentDomainModel student = null;

    if (criteria != null) {
      student = new StudentDomainModel();

      final Map<Subject, Integer> marks = new HashMap<>();

      generateByCourseCriteria(criteria, student);
      generateByAgeCriteria(criteria, student);
      generateByMarksWrapperCriteria(criteria, marks);
      student.setName(studentParametersGenerator.generateStudentName());
      student.setSurname(studentParametersGenerator.generateStudentSurname());

      student.setMarks(marks);
    }

    return student;
  }

  private void generateByCourseCriteria(final StudentClassCriteria criteria, final StudentDomainModel student) {
    if (criteria != null && student != null) {
      if (criteria.getCourseCriteria() != null) {
        int minCourse = (int) criteria.getCourseCriteria().getMin();
        int maxCourse = (int) criteria.getCourseCriteria().getMax();

        student.setCourse(studentParametersGenerator.generateStudentCourse(minCourse, maxCourse));
      } else {
        student.setCourse(studentParametersGenerator.generateRandomStudentCourse());
      }
    }
  }

  private void generateByAgeCriteria(final StudentClassCriteria criteria, final StudentDomainModel student) {
    if (criteria != null && student != null) {
      if (criteria.getAgeCriteria() != null) {
        int minAge = (int) criteria.getAgeCriteria().getMin();
        int maxAge = (int) criteria.getAgeCriteria().getMax();

        student.setAge(studentParametersGenerator.generateStudentAgeByCourseAndRange(student.getCourse(), minAge, maxAge));
      } else {
        student.setAge(studentParametersGenerator.generateStudentAgeByCourse(student.getCourse()));
      }
    }
  }

  private int[] generateMarksFromAverageAlgorithm(double min, double max, final int count) {
    int[] result = null;
    int minMark = (int) studentPropertiesHolder.getStudentMarkRange().getMin();
    int maxMark = (int) studentPropertiesHolder.getStudentMarkRange().getMax();

    if (min <= count * maxMark && max >= minMark && count > 0) {
      result = new int[count];

      for (int i = 0; i < count; i++) {
        if (i == count - 1) {
          result[i] = studentParametersGenerator.generateStudentMark((int) min, (int) max);
        } else if (min < maxMark) {
          if (max > maxMark) {
            max -= maxMark;
          }
          result[i] = minMark;
        } else {
          result[i] = maxMark;
          min -= maxMark;
          max -= maxMark;
        }
      }
    }

    return result;
  }

  private void generateByGroupOperationsCriteria(final StudentClassCriteria criteria, final Map<Subject, Integer> marks) {
    if (criteria != null && marks != null) {
      if (criteria.getStudentMarksWrapperCriteria().getGroupOperationsCriteria() != null) {
        Map<GroupOperation, StudentRangeCriteria> groupOpCriteria = criteria
            .getStudentMarksWrapperCriteria()
            .getGroupOperationsCriteria();

        if (groupOpCriteria.containsKey(GroupOperation.AVERAGE)) {
          int count = Subject.values().length;
          final List<Subject> generatedSubjects = Arrays.asList(Subject.values());
          double minAvgRangeValue = groupOpCriteria
              .get(GroupOperation.AVERAGE)
              .getMin() * count;
          double maxAvgRangeValue = groupOpCriteria
              .get(GroupOperation.AVERAGE)
              .getMax() * count;

          if (criteria.getStudentMarksWrapperCriteria().getMarksCriteria() != null) {
            Map<Subject, StudentRangeCriteria> marksCriteria = criteria
                .getStudentMarksWrapperCriteria()
                .getMarksCriteria();

            for (Map.Entry<Subject, StudentRangeCriteria> marksCriteriaElement : marksCriteria.entrySet()) {
              maxAvgRangeValue -= marksCriteriaElement.getValue().getMin();
              minAvgRangeValue -= marksCriteriaElement.getValue().getMax();
              generatedSubjects.remove(marksCriteriaElement.getKey());
              count--;
            }
          }

          final int[] generatedMarks = generateMarksFromAverageAlgorithm(minAvgRangeValue, maxAvgRangeValue, count);

          for (int i = 0; i < count; i++) {
            marks.put(generatedSubjects.get(i), generatedMarks[i]);
          }
        }
      }
    }
  }

  private void generateByMarksCriteria(final StudentClassCriteria criteria, final Map<Subject, Integer> marks) {
    if (criteria != null && marks != null) {
      if (criteria.getStudentMarksWrapperCriteria().getMarksCriteria() != null) {
        Map<Subject, StudentRangeCriteria> marksCriteria = criteria
            .getStudentMarksWrapperCriteria()
            .getMarksCriteria();

        for (Subject subject : Subject.values()) {
          if (marksCriteria.containsKey(subject)) {
            int minMark = (int) marksCriteria.get(subject).getMin();
            int maxMark = (int) marksCriteria.get(subject).getMax();

            marks.put(subject, studentParametersGenerator.generateStudentMark(minMark, maxMark));
          } else {
            marks.put(subject, studentParametersGenerator.generateRandomStudentMark());
          }
        }
      }
    }
  }

  private void generateByMarksWrapperCriteria(final StudentClassCriteria criteria, final Map<Subject, Integer> marks) {
    if (criteria != null && marks != null) {
      if (criteria.getStudentMarksWrapperCriteria() != null) {
        generateByGroupOperationsCriteria(criteria, marks);
        generateByMarksCriteria(criteria, marks);
      } else {
        for (Subject subject : Subject.values()) {
          marks.put(subject, studentParametersGenerator.generateRandomStudentMark());
        }
      }
    }
  }
}
