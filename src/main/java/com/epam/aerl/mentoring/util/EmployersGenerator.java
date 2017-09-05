package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.factory.EmployerFilterFactory;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.type.EmployerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component("employersGenerator")
public class EmployersGenerator {
    @Autowired
    @Qualifier("employerFilterFactory")
    private EmployerFilterFactory factory;

	public List<EmployerFilter> generate(final Set<EmployerType> types) {
		final List<EmployerFilter> employers = new ArrayList<>();
		
		for (EmployerType type : types) {
			employers.add(factory.getEmployerFilter(type));
		}
		
		return employers;
	}
}
