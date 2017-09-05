package com.epam.aerl.mentoring.factory;

import com.epam.aerl.mentoring.filter.CompanyDirectorFilter;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.filter.MilitaryCommissionerFilter;
import com.epam.aerl.mentoring.filter.ProfessorMathFilter;
import com.epam.aerl.mentoring.type.EmployerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("employerFilterFactory")
public class EmployerFilterFactory {
    @Autowired
    @Qualifier("professorMathFilter")
    private ProfessorMathFilter professorMathFilter;

    @Autowired
    @Qualifier("militaryCommissionerFilter")
    private MilitaryCommissionerFilter militaryCommissionerFilter;

    @Autowired
    @Qualifier("companyDirectorFilter")
    private CompanyDirectorFilter companyDirectorFilter;

    public EmployerFilter getEmployerFilter(final EmployerType type) {
        EmployerFilter filter;

        switch (type) {
            case PROFESSOR:
                filter = professorMathFilter;
                break;
            case MILITARY_COMMISSIONER:
                filter = militaryCommissionerFilter;
                break;
            case COMPANY_DIRECTOR:
                filter = companyDirectorFilter;
                break;
            default:
                filter = null;
        }

        return filter;
    }
}
