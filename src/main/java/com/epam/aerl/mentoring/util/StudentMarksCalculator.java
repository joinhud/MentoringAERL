package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.type.Subject;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("studentMarksCalculator")
public class StudentMarksCalculator {
	public Double calculateAverageSciencesMark(final Map<Subject, Integer> marks) {
		Double result = null;

		if (MapUtils.isNotEmpty(marks)) {
			int subjectCount = 0;
			int marksSum = 0;
			
			for(Map.Entry<Subject, Integer> entry : marks.entrySet()) {
				final Subject subject = entry.getKey();
				final Integer mark = entry.getValue();
				
				if (subject == null || mark == null) {
					return result;
				} else if (subject.isSciences()) {
					subjectCount++;
					marksSum += mark;
				}
			}
			
			result = (double) (marksSum / subjectCount);
		}
		
		return result;
	}
	
	public Integer calculateAverageMark(final Map<Subject, Integer> marks) {
		Integer result = null;
		
		if (MapUtils.isNotEmpty(marks)) {
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
