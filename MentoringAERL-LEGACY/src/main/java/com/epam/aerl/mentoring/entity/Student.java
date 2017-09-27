package com.epam.aerl.mentoring.entity;

import java.io.Serializable;
import java.util.Map;

import com.epam.aerl.mentoring.type.Subject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class Student implements Serializable {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (age != student.age) return false;
        if (course != student.course) return false;
        return marks != null ? marks.equals(student.marks) : student.marks == null;
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + course;
        result = 31 * result + (marks != null ? marks.hashCode() : 0);
        return result;
    }

    @Override
	public String toString() {
		return "Student [age=" + age + ", course=" + course + ", marks=" + marks + "]";
	}
}
