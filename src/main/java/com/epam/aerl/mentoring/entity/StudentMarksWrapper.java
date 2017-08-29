package com.epam.aerl.mentoring.entity;

import com.epam.aerl.mentoring.type.Subject;

import java.util.Map;

public class StudentMarksWrapper {
    private Map<Subject, StudentRangeCriteria> marksCriteria;
    private Map<GroupOperation, StudentRangeCriteria> groupOperationsCriteria;

    private StudentMarksWrapper() {
    }

    public Map<Subject, StudentRangeCriteria> getMarksCriteria() {
        return marksCriteria;
    }

    public Map<GroupOperation, StudentRangeCriteria> getGroupOperationsCriteria() {
        return groupOperationsCriteria;
    }

    public static StudentMarksWrapperBuilder newBuilder() {
        return new StudentMarksWrapper().new StudentMarksWrapperBuilder();
    }

    public class StudentMarksWrapperBuilder {
        private StudentMarksWrapperBuilder() {
        }

        public StudentMarksWrapperBuilder marksCriteria(final Map<Subject, StudentRangeCriteria> newMarksCriteria) {
            StudentMarksWrapper.this.marksCriteria = newMarksCriteria;
            return this;
        }

        public StudentMarksWrapperBuilder groupOperationsCriteria(
                final Map<GroupOperation, StudentRangeCriteria> newGroupOperationsCriteria) {
            StudentMarksWrapper.this.groupOperationsCriteria = newGroupOperationsCriteria;
            return this;
        }

        public StudentMarksWrapper build() {
            StudentMarksWrapper wrapper = new StudentMarksWrapper();
            wrapper.marksCriteria = StudentMarksWrapper.this.marksCriteria;
            wrapper.groupOperationsCriteria = StudentMarksWrapper.this.groupOperationsCriteria;

            return wrapper;
        }
    }

    public enum GroupOperation {
        AVERAGE, SUM, AVERAGE_SCIENCES
    }
}
