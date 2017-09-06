package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.StudentsGeneratorException;

import java.util.List;
import java.util.Map;

public interface StudentsGenerator {
    List<Student> generateStudents(final Map<String, Integer> criteria) throws StudentsGeneratorException;
}
