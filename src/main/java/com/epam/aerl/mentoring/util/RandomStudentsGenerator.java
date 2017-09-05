package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("randomStudentsGenerator")
public class RandomStudentsGenerator {
	private static final String STUDENTS_NUM_ERR_MSG = "The number of students is negative.";

	@Autowired
    @Qualifier("studentParametersGenerator")
	private StudentParametersGenerator generator;

	@Autowired
    @Qualifier("studentPropertiesHolder")
	private StudentPropertiesHolder holder;

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

		final int firstCourse = (int) holder.getStudentCourseRange().getMin();
		final int secondCourse = (int) holder.getStudentCourseRange().getMax();

		final Integer course = generator.generateStudentCourse(firstCourse, secondCourse);
		Integer age = null;
        final Map<Subject, Integer> marks = new HashMap<>();

		if (course != null) {
            age = generator.generateStudentAgeByCourse(course);

            int minMark = (int) holder.getStudentMarkRange().getMin();
            int maxMark = (int) holder.getStudentMarkRange().getMax();

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
