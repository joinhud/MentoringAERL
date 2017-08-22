package com.epam.aerl.mentoring.util;

import java.util.Map;

import com.epam.aerl.mentoring.type.Subject;

public class StudentMarksCalculator {
	public double calculateAverageSciensesMark(Map<Subject, Integer> marks) {
		double result = -1;
		
		if (marks != null && !marks.isEmpty()) {
			int subjectCount = 0;
			int marksSum = 0;
			
			for(Map.Entry<Subject, Integer> entry : marks.entrySet()) {
				if (entry.getKey().isScienses()) {
					subjectCount++;
					marksSum += entry.getValue();
				}
			}
			
			result = marksSum / subjectCount;
		}
		
		return result;
	}
	
	public int calculateAvarageMark(Map<Subject, Integer> marks) {
		int result = -1;
		
		if (marks != null && !marks.isEmpty()) {
			int marksSum = 0;
			
			for(Map.Entry<Subject, Integer> entry : marks.entrySet()) {
				marksSum += entry.getValue();
			}
			
			result = marksSum / marks.size();
		}
		
		return result;
	}
}
