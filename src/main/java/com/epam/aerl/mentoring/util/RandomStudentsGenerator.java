package com.epam.aerl.mentoring.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.BusinessLogicException;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomStudentsGenerator {
	private static final String STUDENTS_NUM_ERR_MSG = "The number of students is negative.";
	private static final String PROPERTY_FILE_ERR_MSG = "Can not read properties from file.";

	private static final String MIN_MARK = "student.min.mark";
	private static final String MAX_MARK = "student.max.mark";

	private static final int FIRST_COURSE = 1;
	private static final int FIFTH_COURSE = 5;

	private static final Properties PROPERTIES = new Properties();
	private static final Logger LOG = LogManager.getLogger(RandomStudentsGenerator.class);

	private StudentParametersGenerator generator = new StudentParametersGenerator();

	public void init() throws BusinessLogicException {
		try {
			PROPERTIES.load(new FileInputStream("src/main/resources/students.properties"));
		} catch (IOException e) {
			LOG.error(e);
			throw new BusinessLogicException(ErrorMessage.FILE_ERROR.getCode(), PROPERTY_FILE_ERR_MSG, e);
		}
	}

	public List<Student> generateStudents(final int studentsNumber) throws StudentsGeneratorException {
		if (studentsNumber < 0) {
			throw new StudentsGeneratorException(ErrorMessage.STUDENTS_NUMBER_ERROR.getCode(), STUDENTS_NUM_ERR_MSG);
		}

		final List<Student> students = new ArrayList<>();

		for(int i = 0; i < studentsNumber; i++) {
			students.add(generateStudent());
		}

		return students;
	}

	private Student generateStudent() {
		Student student = null;

		final Integer course = generator.generateStudentCourse(FIRST_COURSE, FIFTH_COURSE);
		Integer age = null;
        final Map<Subject, Integer> marks = new HashMap<>();

		if (course != null) {
            age = generator.generateStudentAgeByCourse(course);

            int minMark = Integer.valueOf(PROPERTIES.getProperty(MIN_MARK));
            int maxMark = Integer.valueOf(PROPERTIES.getProperty(MAX_MARK));

            marks.put(Subject.MATH, generator.generateStudentMark(minMark, maxMark));
            marks.put(Subject.PHILOSOPHY, generator.generateStudentMark(minMark, maxMark));
            marks.put(Subject.PHYSICAL_EDUCATION, generator.generateStudentMark(minMark, maxMark));
        }

        if (age != null) {
            student = new Student(age, course, marks);
        }

		return student;
	}
}
