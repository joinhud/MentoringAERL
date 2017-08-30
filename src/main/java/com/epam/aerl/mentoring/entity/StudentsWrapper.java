package com.epam.aerl.mentoring.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StudentsWrapper implements Serializable {
    private String name;
    private Date date;
    private List<Student> students;

    private StudentsWrapper() {
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public List<Student> getStudents() {
        return students;
    }

    public static StudentsWrapperBuilder createStudentsWrapper() {
        return new StudentsWrapper().new StudentsWrapperBuilder();
    }

    public class StudentsWrapperBuilder {
        private StudentsWrapperBuilder() {
        }

        public StudentsWrapperBuilder name(final String newName) {
            StudentsWrapper.this.name = newName;
            return this;
        }

        public StudentsWrapperBuilder date(final Date newDate) {
            StudentsWrapper.this.date = newDate;
            return this;
        }

        public StudentsWrapperBuilder students(final List<Student> newStudents) {
            StudentsWrapper.this.students = newStudents;
            return this;
        }

        public StudentsWrapper build() {
            StudentsWrapper wrapper = new StudentsWrapper();
            wrapper.name = StudentsWrapper.this.name;
            wrapper.date = StudentsWrapper.this.date;
            wrapper.students = StudentsWrapper.this.students;

            return wrapper;
        }
    }
}
