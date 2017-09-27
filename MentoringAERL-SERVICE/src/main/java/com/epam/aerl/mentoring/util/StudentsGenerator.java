package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentDomainModel;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;

import java.util.List;
import java.util.Map;

public interface StudentsGenerator {
    List<StudentDomainModel> generateStudents(final Map<String, Integer> criteria) throws StudentsGeneratorException;
}
