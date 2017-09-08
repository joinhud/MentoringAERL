package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.BusinessLogicException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service("printer")
public class Printer {
    private static final String FILE_NAME = "output.txt";
    private static final String OUTPUT_FILE_ERR_MSG = "Cannot get access to the output file.";
    private static final String ENDING = "#######################################################################";
    private static final Logger LOG = LogManager.getLogger(Printer.class);

    public void printStudentData(final Student student) {
        writeToFile(student.toString());
	}
	
	public void printCaption(final String caption) {
        writeToFile(caption);
	}

	public void printEnding() {
        writeToFile(ENDING);
    }
	
	public void printErrorMessage(final ErrorMessage errorMessage) {
        writeToFile(errorMessage.getMessage());
	}

	private void writeToFile(final String data) {
        PrintWriter printWriter = null;

        try {
            final FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(data);
        } catch (IOException e) {
            LOG.error(e);
            throw new BusinessLogicException(ErrorMessage.OUTPUT_FILE_ERROR.getCode(), OUTPUT_FILE_ERR_MSG, e);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
