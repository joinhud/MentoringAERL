package com.epam.aerl.mentoring.entity;

import java.util.Map;

import com.epam.aerl.mentoring.type.Subject;

public class Student {
	private int age;
	private int course;
	private Map<Subject, Integer> marks;

	public Student() {
		super();
	}
	
	public Student(int age, int course, Map<Subject, Integer> marks) {
		super();
		this.age = age;
		this.course = course;
		this.marks = marks;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getCourse() {
		return course;
	}

	public void setCourse(int course) {
		this.course = course;
	}
	
	public Map<Subject, Integer> getMarks() {
		return marks;
	}

	public void setMarks(Map<Subject, Integer> marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "Student [age=" + age + ", course=" + course + ", marks=" + marks + "]";
	}
}
