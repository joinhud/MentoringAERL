package com.epam.aerl.mentoring.util;

import java.util.Map;

import com.epam.aerl.mentoring.type.Subject;

public class StudentMarksCalculator {
	public Double calculateAverageSciensesMark(final Map<Subject, Integer> marks) {
		Double result = null;
		
		if (marks != null && !marks.isEmpty()) {
			int subjectCount = 0;
			int marksSum = 0;
			
			for(Map.Entry<Subject, Integer> entry : marks.entrySet()) {
				final Subject subject = entry.getKey();
				final Integer mark = entry.getValue();
				
				if (subject == null || mark == null) {
					return result;
				} else if (subject.isScienses()) {
					subjectCount++;
					marksSum += mark;
				}
			}
			
			result = (double) (marksSum / subjectCount);
		}
		
		return result;
	}
	
	public Integer calculateAvarageMark(final Map<Subject, Integer> marks) {
		Integer result = null;
		
		if (marks != null && !marks.isEmpty()) {
			int marksSum = 0;
			
			for(Map.Entry<Subject, Integer> entry : marks.entrySet()) {
				final Integer mark = entry.getValue();
				
				if (mark != null) {
					marksSum += entry.getValue();
				} else {
					return result;
				}
			}
			
			result = marksSum / marks.size();
		}
		
		return result;
	}
}
