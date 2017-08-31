package com.epam.aerl.mentoring.controller;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.exception.StudentClassCriteriaException;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.service.SerializableStudentsService;
import com.epam.aerl.mentoring.service.StudentsService;
import com.epam.aerl.mentoring.type.EmployerType;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.util.CriteriaAnalyser;
import com.epam.aerl.mentoring.util.CriteriaStudentsGenerator;
import com.epam.aerl.mentoring.util.EmployersGenerator;
import com.epam.aerl.mentoring.util.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentsController {
    private static final String INPUT_CRITERIA_STRING = "The input criteria line: ";

	private static final Logger LOG = LogManager.getLogger(StudentsController.class);

	private EmployersGenerator employersGenerator = new EmployersGenerator();
	private CriteriaStudentsGenerator criteriaStudentsGenerator = new CriteriaStudentsGenerator();
	private StudentsService service = new StudentsService();
	private Printer printer = new Printer();
	private CriteriaAnalyser analyser = new CriteriaAnalyser();
	private SerializableStudentsService serializableService = new SerializableStudentsService();

	public void takeStudents(String criteriaString) {
        Map<String, Integer> parsedCriteria = analyser.parse(criteriaString);
		
		try {
            if (parsedCriteria != null) {
                criteriaString = analyser.sortCriteria(parsedCriteria);
            	LOG.debug(INPUT_CRITERIA_STRING + criteriaString);

                List<EmployerFilter> employers = employersGenerator.generate(generateRequirements());
                List<Student> students = serializableService.getStudentsList(criteriaString);

                if (students == null) {
                    parsedCriteria = analyser.validate(parsedCriteria);
                    students = criteriaStudentsGenerator.generateStudents(parsedCriteria);
                    serializableService.writeStudentsList(students, criteriaString);
                }

                service.takeStudentsFromUniversity(employers, students);
            }
		} catch (ServiceException e) {
		    LOG.error(e);
			printer.printErrorMessage(ErrorMessage.getByCode(e.getCode()));
		} catch (StudentClassCriteriaException e) {
		    LOG.info(e);
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
