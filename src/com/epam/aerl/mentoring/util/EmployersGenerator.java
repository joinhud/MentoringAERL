package com.epam.aerl.mentoring.util;

import java.util.ArrayList;
import java.util.List;

import com.epam.aerl.mentoring.filter.CompanyDirectorFilter;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.filter.MilitaryCommissionerFilter;
import com.epam.aerl.mentoring.filter.ProfessorMathFilter;

public class EmployersGenerator {
	public List<EmployerFilter> generate() {
		List<EmployerFilter> employers = new ArrayList<>();
		
		employers.add(new ProfessorMathFilter());
		employers.add(new MilitaryCommissionerFilter());
		employers.add(new CompanyDirectorFilter());
		
		return employers;
	}
}
