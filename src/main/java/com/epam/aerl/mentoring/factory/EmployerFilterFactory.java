package com.epam.aerl.mentoring.factory;

import com.epam.aerl.mentoring.filter.CompanyDirectorFilter;
import com.epam.aerl.mentoring.filter.EmployerFilter;
import com.epam.aerl.mentoring.filter.MilitaryCommissionerFilter;
import com.epam.aerl.mentoring.filter.ProfessorMathFilter;
import com.epam.aerl.mentoring.type.EmployerType;

public class EmployerFilterFactory {
    public EmployerFilter getEmployerFilter(final EmployerType type) {
        EmployerFilter filter;

        switch (type) {
            case PROFESSOR:
                filter = new ProfessorMathFilter();
                break;
            case MILITARY_COMMISSIONER:
                filter = new MilitaryCommissionerFilter();
                break;
            case COMPANY_DIRECTOR:
                filter = new CompanyDirectorFilter();
                break;
            default:
                filter = null;
        }

        return filter;
    }
}
