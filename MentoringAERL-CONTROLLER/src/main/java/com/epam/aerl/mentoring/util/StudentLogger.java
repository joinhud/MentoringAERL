package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.ServiceLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Service("studentLogger")
public class StudentLogger extends EntityLogger<Student> {
  private static final Logger LOG = LogManager.getLogger(StudentLogger.class);
  private static final String STUDENT = "student";
  private static final String LOG_STUDENT_TO_XML_ERR_MSG = "Cannot write student to xml file.";

  @Override
  public boolean logEntity(final Student entity) throws ServiceLayerException {
    boolean result = false;

    if (entity != null) {
      final String fileName = PATH + currentDate.format(formatter) + FILE_NAME_SEPARATOR + STUDENT + FILE_NAME_SEPARATOR + entity.getId() + EXTENSION;

      try {
        final File dirPath = new File(PATH);
        dirPath.mkdirs();
        final File file = new File(fileName);
        file.createNewFile();
        final JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);
        final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(entity, file);

        result = true;
      } catch (JAXBException | IOException e) {
        LOG.error(e);
        throw new ServiceLayerException(ErrorMessage.OUTPUT_FILE_ERROR.getCode(), LOG_STUDENT_TO_XML_ERR_MSG, e);
      }
    }

    return result;
  }
}
