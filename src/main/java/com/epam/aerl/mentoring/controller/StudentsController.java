package com.epam.aerl.mentoring.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.service.StudentsService;
import com.epam.aerl.mentoring.type.EmployerType;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.EmployersGenerator;
import com.epam.aerl.mentoring.util.Printer;
import com.epam.aerl.mentoring.util.StudentsGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StudentsController {
	private static final int STUDENTS_COUNT = 50;

	private static final Logger LOG = LogManager.getLogger(StudentsController.class);

	private EmployersGenerator employersGenerator = new EmployersGenerator();
	private StudentsGenerator studentsGenerator = new StudentsGenerator();

	private StudentsService service = new StudentsService();
	private Printer printer = new Printer();

	public void takeStudents() {
		studentsGenerator.init();
		
		try {
			List<EmployerFilter> employers = employersGenerator.generate(generateRequirements());
			List<Student> students = studentsGenerator.generateStudents(STUDENTS_COUNT);
			
			service.takeStudentsFromUniversity(employers, students);
		} catch (ServiceException e) {
		    LOG.error(e);
			printer.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		} catch (StudentsGeneratorException e) {
		    LOG.error(e);
			printer.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		}
	}

	private Set<EmployerType> generateRequirements() {
	    final Set<EmployerType> types = new LinkedHashSet<>();

	    types.add(EmployerType.PROFESSOR);
	    types.add(EmployerType.MILITARY_COMMISSIONER);
	    types.add(EmployerType.COMPANY_DIRECTOR);

	    return types;
    }
}
