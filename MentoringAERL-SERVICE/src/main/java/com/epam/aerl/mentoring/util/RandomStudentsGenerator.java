package com.epam.aerl.mentoring.util;

import static com.epam.aerl.mentoring.type.GenerationClass.S;

import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("randomStudentsGenerator")
public class RandomStudentsGenerator implements StudentsGenerator {
  private static final String STUDENTS_NUM_ERR_MSG = "The number of students is negative.";

  @Autowired
  @Qualifier("studentParametersGenerator")
  private StudentParametersGenerator studentParametersGenerator;

  public void setStudentParametersGenerator(StudentParametersGenerator studentParametersGenerator) {
    this.studentParametersGenerator = studentParametersGenerator;
  }

  @Override
  public List<StudentDomainModel> generateStudents(final Map<String, Integer> criteria) throws StudentsGeneratorException {
    if (criteria.get(S.toString()) == null && criteria.get(S.toString()) < 0) {
      throw new StudentsGeneratorException(ErrorMessage.STUDENTS_NUMBER_ERROR.getCode(), STUDENTS_NUM_ERR_MSG);
    }

    final List<StudentDomainModel> students = new ArrayList<>();
    final int studentsCount = criteria.get(S.toString());

    for (int i = 0; i < studentsCount; i++) {
      students.add(generateStudent());
    }

    return students;
  }

  private StudentDomainModel generateStudent() {
    StudentDomainModel result = new StudentDomainModel();
    result.setName(studentParametersGenerator.generateStudentName());
    result.setSurname(studentParametersGenerator.generateStudentSurname());
    result.setCourse(studentParametersGenerator.generateRandomStudentCourse());
    result.setAge(studentParametersGenerator.generateStudentAgeByCourse(result.getCourse()));

    Map<Subject, Integer> marks = new HashMap<>();

    for (Subject subject : Subject.values()) {
      marks.put(subject, studentParametersGenerator.generateRandomStudentMark());
    }

    result.setMarks(marks);

    return result;
  }
}
