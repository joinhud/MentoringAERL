package com.epam.aerl.mentoring;

import com.epam.aerl.mentoring.config.AppConfig;
import com.epam.aerl.mentoring.controller.StudentsController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Main {
	private static final int FIRST_ARG = 0;
	private static final String CONTROLLER_BEAN_NAME = "studentsController";
	
	public static void main(String[] args) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        StudentsController controller = (StudentsController) context.getBean(CONTROLLER_BEAN_NAME);
        String arg = null;

        if (args.length != 0) {
            arg = args[FIRST_ARG];
        }

        controller.takeStudents(arg);
	}

}
