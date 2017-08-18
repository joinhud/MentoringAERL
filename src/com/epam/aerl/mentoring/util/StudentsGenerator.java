package com.epam.aerl.mentoring.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;

public class StudentsGenerator {
	private static final String STUDENTS_NUM_ERR_MSG = "The number of students is negative.";
	
	private static final String MIN_COURCE = "student.min.course";
	private static final String MAX_COURCE = "student.max.course";
	private static final String FIRST_COURSE_MIN_AGE = "student.min.age.first.course";
	private static final String FIRST_COURSE_MAX_AGE = "student.max.age.first.course";
	private static final String SECOND_COURSE_MIN_AGE = "student.min.age.second.course";
	private static final String SECOND_COURSE_MAX_AGE = "student.max.age.second.course";
	private static final String THIRD_COURSE_MIN_AGE = "student.min.age.third.course";
	private static final String THIRD_COURSE_MAX_AGE = "student.max.age.third.course";
	private static final String FOURTH_COURSE_MIN_AGE = "student.min.age.fourth.course";
	private static final String FOURTH_COURSE_MAX_AGE = "student.max.age.fourth.course";
	private static final String FIFTH_COURSE_MIN_AGE = "student.min.age.fifth.course";
	private static final String FIFTH_COURSE_MAX_AGE = "student.max.age.fifth.course";
	private static final String MIN_MARK = "student.min.mark";
	private static final String MAX_MARK = "student.max.mark";
	
	private static final int FIRST_COURSE = 1;
	private static final int SECOND_COURSE = 2;
	private static final int THIRD_COURSE = 3;
	private static final int FOURTH_COURSE = 4;
	private static final int FIFTH_COURSE = 5;
	
	private static final Properties PROPERTIES = new Properties();
	
	private static final Random RANDOM = new Random();

	public StudentsGenerator() throws FileNotFoundException, IOException {
		PROPERTIES.load(new FileInputStream("resources/students.properties"));
	}
	
	public List<Student> generateStudents(int studentsNumber) throws StudentsGeneratorException {
		if (studentsNumber < 0) {
			throw new StudentsGeneratorException(STUDENTS_NUM_ERR_MSG);
		}
		
		List<Student> students = new ArrayList<>();
		
		for(int i = 0; i < studentsNumber; i++) {
			students.add(generateStudent());
		}
		
		return students;
	}
	
	private Student generateStudent() {
		Student student = null;
		
		int course = generateCourse();
		int age = generateStudentAge(course);
		
		if (age > 0) {
			student = new Student();
			student.setAge(age);
			student.setCourse(course);
			student.setMathMark(generateMark());
			student.setPhilosophyMark(generateMark());
			student.setPhysicalEducationMark(generateMark());
		}
		
		return student;
	}
	

	private int generateCourse() {
		int minCourse = Integer.valueOf(PROPERTIES.getProperty(MIN_COURCE));
		int maxCourse = Integer.valueOf(PROPERTIES.getProperty(MAX_COURCE));
		
		return RANDOM.nextInt((maxCourse - minCourse) + 1) + minCourse;
	}
	
	private int generateStudentAge(int course) {
		int age;
		int minAge;
		int maxAge;
		
		switch (course) {
			case FIRST_COURSE: 
				minAge = Integer.valueOf(PROPERTIES.getProperty(FIRST_COURSE_MIN_AGE));
				maxAge = Integer.valueOf(PROPERTIES.getProperty(FIRST_COURSE_MAX_AGE));
				break;
			case SECOND_COURSE:
				minAge = Integer.valueOf(PROPERTIES.getProperty(SECOND_COURSE_MIN_AGE));
				maxAge = Integer.valueOf(PROPERTIES.getProperty(SECOND_COURSE_MAX_AGE));
				break;
			case THIRD_COURSE:
				minAge = Integer.valueOf(PROPERTIES.getProperty(THIRD_COURSE_MIN_AGE));
				maxAge = Integer.valueOf(PROPERTIES.getProperty(THIRD_COURSE_MAX_AGE));
				break;
			case FOURTH_COURSE:
				minAge = Integer.valueOf(PROPERTIES.getProperty(FOURTH_COURSE_MIN_AGE));
				maxAge = Integer.valueOf(PROPERTIES.getProperty(FOURTH_COURSE_MAX_AGE));
				break;
			case FIFTH_COURSE:
				minAge = Integer.valueOf(PROPERTIES.getProperty(FIFTH_COURSE_MIN_AGE));
				maxAge = Integer.valueOf(PROPERTIES.getProperty(FIFTH_COURSE_MAX_AGE));
				break;
			default:
				minAge = 0;
				maxAge = 0;
				break;
		}
		
		age = minAge != maxAge ? RANDOM.nextInt((maxAge - minAge) + 1) + minAge : minAge;
		
		return age;
	}
	
	private int generateMark() {
		int minMark = Integer.valueOf(PROPERTIES.getProperty(MIN_MARK));
		int maxMark = Integer.valueOf(PROPERTIES.getProperty(MAX_MARK));
		
		return RANDOM.nextInt((maxMark - minMark) + 1) + minMark;
	}
}
