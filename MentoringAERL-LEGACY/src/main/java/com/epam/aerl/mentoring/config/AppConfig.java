package com.epam.aerl.mentoring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.epam.aerl.mentoring")
@PropertySource("classpath:students.properties")
public class AppConfig {
}
