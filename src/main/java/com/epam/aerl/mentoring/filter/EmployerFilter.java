package com.epam.aerl.mentoring.filter;

import java.util.Iterator;
import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.util.Printer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EmployerFilter {
    private static final String START_FILTER_LOG_MSG = "Starting filtering students...";
    private static final String TOOK_LOG_MSG = "Took {} students.";

	private static final Printer PRINTER = new Printer();
	private final Logger log = LogManager.getLogger(this.getClass());
	
	public void takeAway(final List<Student> students) {
		if (CollectionUtils.isNotEmpty(students)) {
            log.debug(START_FILTER_LOG_MSG);

		    final int beforeCount = students.size();
			Iterator<Student> iterator = students.iterator();
			
			printEmployerCaption();
			
			while(iterator.hasNext()) {
				Student student = iterator.next();
				
				if (checkCriteria(student)) {
					PRINTER.printStudentData(student);
					iterator.remove();
				}
				
			}

            log.debug(TOOK_LOG_MSG, beforeCount - students.size());
		}
	}
	
	public abstract boolean checkCriteria(final Student student);
	
	public abstract void printEmployerCaption();
}
