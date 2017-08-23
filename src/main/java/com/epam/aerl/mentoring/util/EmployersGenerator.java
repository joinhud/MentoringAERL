package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.factory.EmployerFilterFactory;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.type.EmployerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EmployersGenerator {
    private EmployerFilterFactory factory = new EmployerFilterFactory();

	public List<EmployerFilter> generate(final Set<EmployerType> types) {
		final List<EmployerFilter> employers = new ArrayList<>();
		
		for (EmployerType type : types) {
			employers.add(factory.getEmployerFilter(type));
		}
		
		return employers;
	}
}
