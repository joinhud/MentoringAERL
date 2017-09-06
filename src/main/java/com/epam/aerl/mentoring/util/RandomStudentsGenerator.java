package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.aerl.mentoring.type.GenerationClass.S;

@Service("randomStudentsGenerator")
public class RandomStudentsGenerator implements StudentsGenerator {
	private static final String STUDENTS_NUM_ERR_MSG = "The number of students is negative.";

	@Autowired
    @Qualifier("studentParametersGenerator")
	private StudentParametersGenerator studentParametersGenerator;

	@Autowired
    @Qualifier("studentPropertiesHolder")
	private StudentPropertiesHolder studentPropertiesHolder;

	@Autowired
	private ApplicationContext applicationContext;

    @Override
	public List<Student> generateStudents(final Map<String, Integer> criteria) throws StudentsGeneratorException {
		if (criteria.get(S.toString()) == null && criteria.get(S.toString()) < 0) {
			throw new StudentsGeneratorException(ErrorMessage.STUDENTS_NUMBER_ERROR.getCode(), STUDENTS_NUM_ERR_MSG);
		}

		final List<Student> students = new ArrayList<>();
		final int studentsCount = criteria.get(S.toString());

		for(int i = 0; i < studentsCount; i++) {
			students.add(generateStudent());
		}

		return students;
	}

	private Student generateStudent() {
        Student student = (Student) applicationContext.getBean("student");
        return student;
	}
}
