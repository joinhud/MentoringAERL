package com.epam.aerl.mentoring.service;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.entity.StudentsWrapper;
import com.epam.aerl.mentoring.exception.ServiceException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.epam.aerl.mentoring.entity.StudentsWrapper.createStudentsWrapper;

@Service("serializableStudentsService")
public class SerializableStudentsService {
    private static final String PATH = "data/";
    private static final String EXT = ".stu";
    private static final String STORING_FILE_ERROR_MSG = "Can not get access to file for storing wrapper data";
    private static final int MAX_ALIVE_DAYS = 2;

    private static final Logger LOG = LogManager.getLogger(SerializableStudentsService.class);

    public List<Student> getStudentsList(final String criteriaLine) {
        List<Student> result = null;

        if (criteriaLine != null) {
            try (final FileInputStream fileInputStream = new FileInputStream(PATH + criteriaLine + EXT);
                 final ObjectInputStream in = new ObjectInputStream(fileInputStream)) {

                StudentsWrapper wrapper = (StudentsWrapper) in.readObject();

                Calendar diff = Calendar.getInstance();
                Calendar old = Calendar.getInstance();
                diff.setTime(new Date());
                old.setTime(wrapper.getDate());
                diff.setTimeInMillis(diff.getTimeInMillis() - old.getTimeInMillis());

                if (diff.get(Calendar.DAY_OF_YEAR) < MAX_ALIVE_DAYS) {
                    result = wrapper.getStudents();
                }
            } catch (IOException | ClassNotFoundException e) {
                LOG.info(e);
            }
        }

        return result;
    }

    public void writeStudentsList(final List<Student> students, final String criteriaLine) throws ServiceException {
        if (students != null && criteriaLine != null) {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream out = null;

            try {
                final File dir = new File(PATH);
                dir.mkdir();
                final File file = new File(PATH + criteriaLine + EXT);
                file.createNewFile();

                fileOutputStream = new FileOutputStream(file, false);
                out = new ObjectOutputStream(fileOutputStream);

                StudentsWrapper wrapper = createStudentsWrapper()
                        .name(criteriaLine)
                        .date(new Date())
                        .students(students)
                        .build();

                out.writeObject(wrapper);
            } catch (IOException e) {
                LOG.info(e);
                throw new ServiceException(ErrorMessage.FILE_ERROR.getCode(), STORING_FILE_ERROR_MSG, e);
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }

                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    LOG.info(e);
                    throw new ServiceException(ErrorMessage.FILE_ERROR.getCode(), STORING_FILE_ERROR_MSG, e);
                }
            }
        }
    }
}
