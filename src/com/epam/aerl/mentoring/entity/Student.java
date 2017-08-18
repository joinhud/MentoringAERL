package com.epam.aerl.mentoring.entity;

public class Student {
	private int age;
	private int course;
	private int mathMark;
	private int philosophyMark;
	private int physicalEducationMark;

	public Student() {
		super();
	}

	public Student(int age, int course, int mathMark, int philosophyMark, int physicalEducationMark) {
		this.age = age;
		this.course = course;
		this.mathMark = mathMark;
		this.philosophyMark = philosophyMark;
		this.physicalEducationMark = physicalEducationMark;
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

	public int getMathMark() {
		return mathMark;
	}

	public void setMathMark(int mathMark) {
		this.mathMark = mathMark;
	}

	public int getPhilosophyMark() {
		return philosophyMark;
	}

	public void setPhilosophyMark(int philosophyMark) {
		this.philosophyMark = philosophyMark;
	}

	public int getPhysicalEducationMark() {
		return physicalEducationMark;
	}

	public void setPhysicalEducationMark(int physicalEducationMark) {
		this.physicalEducationMark = physicalEducationMark;
	}
	
	@Override
	public String toString() {
		return "Student [age=" + age + ", course=" + course + ", mathMark=" + mathMark + ", philosophyMark="
				+ philosophyMark + ", physicalEducationMark=" + physicalEducationMark + "]";
	}
}
