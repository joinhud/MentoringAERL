package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.Subject;

import java.util.Map;

public class StudentMarksWrapper {
    private Map<Subject, StudentMarkCriteria> marksCriteria;
    private Map<GroupOperation, StudentMarkCriteria> groupOperationsCriteria;

    public StudentMarksWrapper() {
    }

    public StudentMarksWrapper(Map<Subject, StudentMarkCriteria> marksCriteria, Map<GroupOperation, StudentMarkCriteria> groupOperationsCriteria) {
        this.marksCriteria = marksCriteria;
        this.groupOperationsCriteria = groupOperationsCriteria;
    }

    public Map<Subject, StudentMarkCriteria> getMarksCriteria() {
        return marksCriteria;
    }

    public void setMarksCriteria(Map<Subject, StudentMarkCriteria> marksCriteria) {
        this.marksCriteria = marksCriteria;
    }

    public Map<GroupOperation, StudentMarkCriteria> getGroupOperationsCriteria() {
        return groupOperationsCriteria;
    }

    public void setGroupOperationsCriteria(Map<GroupOperation, StudentMarkCriteria> groupOperationsCriteria) {
        this.groupOperationsCriteria = groupOperationsCriteria;
    }

    public enum GroupOperation {
        AVERAGE, SUM, AVERAGE_SCIENCES
    }
}
