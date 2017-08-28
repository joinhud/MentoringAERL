package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.Subject;

import java.util.Map;

public class StudentMarksWrapper {
    private final Map<Subject, StudentMarkCriteria> marksCriteria;
    private final Map<GroupOperation, StudentMarkCriteria> groupOperationsCriteria;

    private StudentMarksWrapper() {
        this.marksCriteria = null;
        this.groupOperationsCriteria = null;
    }

    private StudentMarksWrapper(final Map<Subject, StudentMarkCriteria> newMarksCriteria,
                               final Map<GroupOperation, StudentMarkCriteria> newGroupOperationsCriteria) {
        this.marksCriteria = newMarksCriteria;
        this.groupOperationsCriteria = newGroupOperationsCriteria;
    }

    public Map<Subject, StudentMarkCriteria> getMarksCriteria() {
        return marksCriteria;
    }

    public Map<GroupOperation, StudentMarkCriteria> getGroupOperationsCriteria() {
        return groupOperationsCriteria;
    }

    public static class StudentMarksWrapperBuilder {
        private Map<Subject, StudentMarkCriteria> builderMarksCriteria;
        private Map<GroupOperation, StudentMarkCriteria> builderGroupOperationsCriteria;

        public StudentMarksWrapperBuilder() {
            this.builderMarksCriteria = null;
            this.builderGroupOperationsCriteria = null;
        }

        public StudentMarksWrapperBuilder marksCriteria(final Map<Subject, StudentMarkCriteria> newMarksCriteria) {
            this.builderMarksCriteria = newMarksCriteria;
            return this;
        }

        public StudentMarksWrapperBuilder groupOperationsCriteria(
                final Map<GroupOperation, StudentMarkCriteria> newGroupOperationsCriteria) {
            this.builderGroupOperationsCriteria = newGroupOperationsCriteria;
            return this;
        }

        public StudentMarksWrapper createMarksWrapper() {
            return new StudentMarksWrapper(builderMarksCriteria, builderGroupOperationsCriteria);
        }
    }

    public enum GroupOperation {
        AVERAGE, SUM, AVERAGE_SCIENCES
    }
}
