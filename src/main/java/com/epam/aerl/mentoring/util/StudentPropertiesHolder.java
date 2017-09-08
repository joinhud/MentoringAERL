package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.StudentRangeCriteria;

import java.util.Map;

public class StudentPropertiesHolder {
    private StudentRangeCriteria studentCourseRange;
    private StudentRangeCriteria studentAgeRange;
    private StudentRangeCriteria studentMarkRange;
    private Map<Integer, StudentRangeCriteria> studentCourseAgeRanges;

    public StudentPropertiesHolder() {
    }

    public StudentRangeCriteria getStudentCourseRange() {
        return studentCourseRange;
    }

    public StudentRangeCriteria getStudentAgeRange() {
        return studentAgeRange;
    }

    public StudentRangeCriteria getStudentMarkRange() {
        return studentMarkRange;
    }

    public Map<Integer, StudentRangeCriteria> getStudentCourseAgeRanges() {
        return studentCourseAgeRanges;
    }

    public static StudentPropertiesHolderBuilder createStudentPropertiesHolderBuilder() {
        return new StudentPropertiesHolder().new StudentPropertiesHolderBuilder();
    }

    public class StudentPropertiesHolderBuilder {
        private StudentPropertiesHolderBuilder() {
        }

        public StudentPropertiesHolderBuilder studentCourseRange(final StudentRangeCriteria newStudentCourseRange) {
            StudentPropertiesHolder.this.studentCourseRange = newStudentCourseRange;
            return this;
        }

        public StudentPropertiesHolderBuilder studentAgeRange(final StudentRangeCriteria newStudentAgeRange) {
            StudentPropertiesHolder.this.studentAgeRange = newStudentAgeRange;
            return this;
        }

        public StudentPropertiesHolderBuilder studentMarkRange(final StudentRangeCriteria newStudentMarkRange) {
            StudentPropertiesHolder.this.studentMarkRange = newStudentMarkRange;
            return this;
        }

        public StudentPropertiesHolderBuilder studentCourseAgeRanges(final Map<Integer, StudentRangeCriteria> newStudentCourseAgeRanges) {
            StudentPropertiesHolder.this.studentCourseAgeRanges = newStudentCourseAgeRanges;
            return this;
        }

        public StudentPropertiesHolder build() {
            StudentPropertiesHolder holder = new StudentPropertiesHolder();
            holder.studentCourseRange = StudentPropertiesHolder.this.studentCourseRange;
            holder.studentAgeRange = StudentPropertiesHolder.this.studentAgeRange;
            holder.studentMarkRange = StudentPropertiesHolder.this.studentMarkRange;
            holder.studentCourseAgeRanges = StudentPropertiesHolder.this.studentCourseAgeRanges;

            return holder;
        }
    }
}
