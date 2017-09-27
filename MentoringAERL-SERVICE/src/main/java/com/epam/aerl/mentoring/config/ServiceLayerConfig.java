package com.epam.aerl.mentoring.config;

import static com.epam.aerl.mentoring.entity.StudentRangeCriteria.createRangeCriteriaBuilder;
import static com.epam.aerl.mentoring.util.StudentPropertiesHolder.createStudentPropertiesHolderBuilder;

import com.epam.aerl.mentoring.entity.StudentRangeCriteria;
import com.epam.aerl.mentoring.util.MarksDozerConverter;
import com.epam.aerl.mentoring.util.StudentPropertiesHolder;
import com.epam.aerl.mentoring.util.UniversityStatusDozerConverter;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ComponentScan(value = {"com.epam.aerl.mentoring.service", "com.epam.aerl.mentoring.util"})
@ImportResource("classpath:dao-spring-context.xml")
@PropertySource({
    "classpath:students.properties",
    "classpath:database.properties",
    "classpath:students.generation.constraints.properties"
})
public class ServiceLayerConfig {
  @Value("${student.min.course}")
  private String minCourse;

  @Value("${student.max.course}")
  private String maxCourse;

  @Value("${student.min.age}")
  private String minAge;

  @Value("${student.max.age}")
  private String maxAge;

  @Value("${student.min.mark}")
  private String minMark;

  @Value("${student.max.mark}")
  private String maxMark;

  @Value("${student.min.age.first.course}")
  private String minFirstCourseAge;

  @Value("${student.max.age.first.course}")
  private String maxFirstCourseAge;

  @Value("${student.min.age.second.course}")
  private String minSecondCourseAge;

  @Value("${student.max.age.second.course}")
  private String maxSecondCourseAge;

  @Value("${student.min.age.third.course}")
  private String minThirdCourseAge;

  @Value("${student.max.age.third.course}")
  private String maxThirdCourseAge;

  @Value("${student.min.age.fourth.course}")
  private String minFourthCourseAge;

  @Value("${student.max.age.fourth.course}")
  private String maxFourthCourseAge;

  @Value("${student.min.age.fifth.course}")
  private String minFifthCourseAge;

  @Value("${student.max.age.fifth.course}")
  private String maxFifthCourseAge;

  @Value("${max.amount}")
  private String maxAmount;

  @Bean
  public StudentPropertiesHolder studentPropertiesHolder() {
    StudentRangeCriteria courseRange = createRangeCriteriaBuilder()
        .min(Double.valueOf(minCourse))
        .max(Double.valueOf(maxCourse))
        .build();
    StudentRangeCriteria ageRange = createRangeCriteriaBuilder()
        .min(Double.valueOf(minAge))
        .max(Double.valueOf(maxAge))
        .build();
    StudentRangeCriteria markRange = createRangeCriteriaBuilder()
        .min(Double.valueOf(minMark))
        .max(Double.valueOf(maxMark))
        .build();
    Map<Integer, StudentRangeCriteria> courseAgeRanges = new LinkedHashMap<>();
    courseAgeRanges.put(1, createRangeCriteriaBuilder()
        .min(Double.valueOf(minFirstCourseAge))
        .max(Double.valueOf(maxFirstCourseAge))
        .build()
    );
    courseAgeRanges.put(2, createRangeCriteriaBuilder()
        .min(Double.valueOf(minSecondCourseAge))
        .max(Double.valueOf(maxSecondCourseAge))
        .build()
    );
    courseAgeRanges.put(3, createRangeCriteriaBuilder()
        .min(Double.valueOf(minThirdCourseAge))
        .max(Double.valueOf(maxThirdCourseAge))
        .build()
    );
    courseAgeRanges.put(4, createRangeCriteriaBuilder()
        .min(Double.valueOf(minFourthCourseAge))
        .max(Double.valueOf(maxFourthCourseAge))
        .build()
    );
    courseAgeRanges.put(5, createRangeCriteriaBuilder()
        .min(Double.valueOf(minFifthCourseAge))
        .max(Double.valueOf(maxFifthCourseAge))
        .build()
    );

    return createStudentPropertiesHolderBuilder()
        .studentCourseRange(courseRange)
        .studentAgeRange(ageRange)
        .studentMarkRange(markRange)
        .studentCourseAgeRanges(courseAgeRanges)
        .build();
  }

  @Bean(name = "maxStudentsAmount")
  public Integer maxStudentsAmount() {
    return Integer.valueOf(maxAmount);
  }

  @Bean(name = "dozerBeanMapper")
  public DozerBeanMapper dozerBeanMapper() {
    DozerBeanMapper mapper = new DozerBeanMapper();
    List<String> mappingFiles = new ArrayList<>();
    mappingFiles.add("mappings/dozer-mapping.xml");
    mapper.setMappingFiles(mappingFiles);
    List<CustomConverter> converters = new ArrayList<>();
    converters.add(new MarksDozerConverter());
    converters.add(new UniversityStatusDozerConverter());
    mapper.setCustomConverters(converters);

    return mapper;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}

