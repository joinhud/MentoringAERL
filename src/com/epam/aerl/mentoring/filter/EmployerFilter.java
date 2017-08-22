package com.epam.aerl.mentoring.filter;

import java.util.Iterator;
import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.util.Printer;

public abstract class EmployerFilter {
	private static final Printer PRINTER = new Printer();
	
	public void takeAway(final List<Student> students) {
		if (students != null && !students.isEmpty()) {
			Iterator<Student> iterator = students.iterator();
			
			printEmployerCaption();
			
			while(iterator.hasNext()) {
				Student student = iterator.next();
				
				if (checkCriteria(student)) {
					PRINTER.printStudentData(student);
					iterator.remove();
				}
				
			}
		}
	}
	
	public abstract boolean checkCriteria(final Student student);
	
	public abstract void printEmployerCaption();
}
