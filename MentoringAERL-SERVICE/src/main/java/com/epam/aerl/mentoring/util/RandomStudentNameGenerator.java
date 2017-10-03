package com.epam.aerl.mentoring.util;

import com.epam.aerl.mentoring.exception.BusinessLogicException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import javax.annotation.PostConstruct;

@Service("randomStudentNameGenerator")
public class RandomStudentNameGenerator {
  private static final String FILE_ERROR_MSG = "Cannot read data from json file with names.";
  private static final Random RANDOM = new Random();
  private static final String NAMES_JSON_FILE = "C:/Work/ProjectSources/MentoringAERL/first-names.json";
  private static final String SURNAME_JSON_FILE = "C:/Work/ProjectSources/MentoringAERL/surnames.json";

  private JSONArray names;
  private JSONArray surnames;

  @PostConstruct
  public void init() {
    final JSONParser parser = new JSONParser();

    try {
      names = (JSONArray) parser.parse(new FileReader(NAMES_JSON_FILE));
      surnames = (JSONArray) parser.parse(new FileReader(SURNAME_JSON_FILE));
    } catch (ParseException | IOException e) {
      throw new BusinessLogicException(ErrorMessage.FILE_ERROR.getCode(), FILE_ERROR_MSG, e);
    }
  }

  public String generateName() {
    return getRandomName(names);
  }

  public String generateSurname() {
    return getRandomName(surnames);
  }

  private String getRandomName(final JSONArray names) {
    final int randomIndex = RANDOM.nextInt(names.size() + 1);

    return (String) names.get(randomIndex);
  }
}
