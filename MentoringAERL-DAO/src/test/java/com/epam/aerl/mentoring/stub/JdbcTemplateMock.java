package com.epam.aerl.mentoring.stub;

import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.entity.UniversityStatusDTO;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class JdbcTemplateMock extends JdbcTemplate {
  public JdbcTemplateMock(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException {
    return 1;
  }

  @Override
  public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {
    if (args[0] == null || args[0].equals(-100L)) {
      return new ArrayList<>();
    } if (args[0].equals(1L)) {
      final UniversityDTO universityDTO = new UniversityDTO();
      universityDTO.setId((Long) args[0]);
      universityDTO.setName("Test");
      universityDTO.setDescription("Test");
      universityDTO.setFoundationDate(LocalDate.of(1888, 8, 8));
      final UniversityStatusDTO statusDTO = new UniversityStatusDTO();
      statusDTO.setId((long) (UniversityStatus.OPEN.ordinal() + 1));
      statusDTO.setStatusName(UniversityStatus.OPEN);
      universityDTO.setUniversityStatusDTO(statusDTO);
      universityDTO.setCreationInDB(LocalDateTime.of(2017, 10, 5, 2, 30));
      universityDTO.setLastUpdateInDB(LocalDateTime.of(2017, 10, 5, 2, 30));

      final List<UniversityDTO> resultList = new ArrayList<>();
      resultList.add(universityDTO);

      return (List<T>) resultList;
    } else {
      return super.query(sql, rowMapper, args);
    }
  }

  @Override
  public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {
    if (args[0].equals(1L)) {
      return null;
    } else {
      return super.query(sql, rse, args);
    }
  }

  @Override
  public int update(String sql, Object... args) throws DataAccessException {
    if (args[5].equals(-100L)) {
      return 0;
    } else {
      return 1;
    }
  }
}
