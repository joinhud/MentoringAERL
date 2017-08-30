package com.epam.aerl.mentoring.service;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentsWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.epam.aerl.mentoring.entity.StudentsWrapper.createStudentsWrapper;

public class SerializableStudentsService {
    private static final String PATH = "data/";
    private static final String EXT = ".stu";
    private static final int MAX_ALIVE = 2;

    private static final Logger LOG = LogManager.getLogger(SerializableStudentsService.class);

    public List<Student> getStudentsList(final String criteriaLine) {
        List<Student> result = null;

        if (criteriaLine != null) {
            try (final FileInputStream fileInputStream = new FileInputStream(PATH + criteriaLine + EXT);
                 final ObjectInputStream in = new ObjectInputStream(fileInputStream)) {

                StudentsWrapper wrapper = (StudentsWrapper) in.readObject();

                Calendar diff = Calendar.getInstance();
                diff.setTimeInMillis(new Date().getTime() - wrapper.getDate().getTime());
                int days = diff.get(Calendar.DAY_OF_YEAR);

                if (days < MAX_ALIVE) {
                    result = wrapper.getStudents();
                }
            } catch (IOException | ClassNotFoundException e) {
                LOG.error(e);
            }
        }

        return result;
    }

    public void writeStudentsList(final List<Student> students, final String criteriaLine) {
        if (students != null) {
            try {
                final File dir = new File(PATH);
                dir.mkdir();
                final File file = new File(PATH + criteriaLine + EXT);
                file.createNewFile();
                final FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                final ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);

                StudentsWrapper wrapper = createStudentsWrapper()
                        .name(criteriaLine)
                        .date(new Date())
                        .students(students)
                        .build();
                
                out.writeObject(wrapper);
                fileOutputStream.close();
                out.close();
            } catch (IOException e) {
                LOG.error(e);
            }
        }
    }
}
