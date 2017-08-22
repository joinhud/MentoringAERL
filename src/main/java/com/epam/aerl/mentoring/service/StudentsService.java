package com.epam.aerl.mentoring.service;

import java.util.List;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.Printer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentsService {
	private static final String CAPTION = "Students which will remain:";
	private static final String REMAIN_LOG_MESSAGE = "Employers took students, remaining {}.";

	private static final String EMPTY_STUDENTS_ERR_MSG = "List of students is empty or null.";
	
	private static final Printer PRINTER = new Printer();
    private static final Logger LOG = LogManager.getLogger();
	
	public void takeStudentsFromUniversity(final List<EmployerFilter> employers, final List<Student> students) throws ServiceException {
		if (CollectionUtils.isNotEmpty(students)) {
			for(EmployerFilter employer : employers) {
				employer.takeAway(students);
			}
			
			printRemainStudents(students);
		} else {
			throw new ServiceException(ErrorMessage.NO_STUDENTS_ERROR.getCode(), EMPTY_STUDENTS_ERR_MSG);
		}
	}
	
	private void printRemainStudents(final List<Student> students) {
		PRINTER.printCaption(CAPTION);
		LOG.debug(REMAIN_LOG_MESSAGE, students.size());
		
		for(Student student : students) {
			PRINTER.printStudentData(student);
			LOG.debug(student);
		}
	}
	
}
