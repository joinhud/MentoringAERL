package com.epam.aerl.mentoring.dao.jdbc;

import com.epam.aerl.mentoring.dao.StudentDao;
import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.entity.StudentMarkDTO;
import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.entity.UniversityStatusDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("studentDaoImpl")
public class StudentDaoImpl extends AbstractDao implements StudentDao {
  private static final String STUDENT_ID = "STDN_ID";
  private static final String STUDENT_NAME = "STDN_NAME";
  private static final String STUDENT_SURNAME = "STDN_SURNAME";
  private static final String STUDENT_AGE = "STDN_AGE";
  private static final String STUDENT_COURSE = "STDN_COURSE";
  private static final String STUDENT_CREATION_IN_DB = "STDN_CREATION_IN_DB";
  private static final String STUDENT_LAST_UPDATE_DB = "STDN_LAST_UPDATE";
  private static final String UNIVERSITY_ID = "STDN_UNVR_ID";
  private static final String UNIVERSITY_NAME = "UNVR_NAME";
  private static final String UNIVERSITY_DESCRIPTION = "UNVR_DESCRIPTION";
  private static final String UNIVERSITY_FOUNDATION_DATE = "UNVR_FOUNDATION_DATE";
  private static final String UNIVERSITY_CREATION_IN_DB = "UNVR_CREATION_IN_DB";
  private static final String UNIVERSITY_LAST_UPDATE_DB = "UNVR_LAST_UPDATE";
  private static final String STUDENT_MARK_ID = "STMRK_ID";
  private static final String STUDENT_MARK_SUBJECT = "STMRK_SUBJECT";
  private static final String STUDENT_MARK_VALUE = "STMRK_MARK";
  private static final String STATUS_ID = "STAT_ID";
  private static final String STATUS_NAME = "STAT_NAME";

  private static final String INCORRECT_INPUT_DATA_ERR_MSG = "Input data is incorrect. StudentDTO name or surname more than 20 symbols.";

  private static final String SELECT_BY_ID_SQL = "SELECT \"STDN_ID\", \"STDN_UNVR_ID\", \"STDN_NAME\", \"STDN_SURNAME\", " +
      "\"STDN_AGE\", \"STDN_COURSE\", \"STDN_CREATION_IN_DB\", \"STDN_LAST_UPDATE\", \"UNVR_NAME\", \"UNVR_DESCRIPTION\", " +
      "\"UNVR_FOUNDATION_DATE\", \"UNVR_CREATION_IN_DB\", \"UNVR_LAST_UPDATE\", \"STAT_ID\", \"STAT_NAME\", \"STMRK_ID\", " +
      "\"STMRK_SUBJECT\", \"STMRK_MARK\" " +
      "FROM \"STUDENT\" LEFT JOIN \"UNIVERSITY\" ON \"STDN_UNVR_ID\" = \"UNVR_ID\" " +
      "LEFT JOIN \"STUDENT_MARK\" ON \"STDN_ID\" = \"STMRK_STDN_ID\" " +
      "JOIN \"UNIVERSITY_STATUS\" ON \"UNVR_STAT_ID\" = \"STAT_ID\" " +
      "WHERE \"STDN_ID\" = ?";
  private static final String SELECT_BY_UNIVERSITY_ID_SQL = "SELECT \"STDN_ID\", \"STDN_NAME\", \"STDN_SURNAME\", " +
      "\"STDN_AGE\", \"STDN_COURSE\", \"STDN_CREATION_IN_DB\", \"STDN_LAST_UPDATE\", \"STMRK_ID\", " +
      "\"STMRK_SUBJECT\", \"STMRK_MARK\" " +
      "FROM \"STUDENT\" LEFT JOIN \"STUDENT_MARK\" ON \"STDN_ID\" = \"STMRK_STDN_ID\" " +
      "WHERE \"STDN_UNVR_ID\" IS NULL";
  private static final String INSERT_STUDENT_SQL = "INSERT INTO \"STUDENT\"(\"STDN_UNVR_ID\", \"STDN_NAME\", " +
      "\"STDN_SURNAME\", \"STDN_AGE\", \"STDN_COURSE\", \"STDN_CREATION_IN_DB\", \"STDN_LAST_UPDATE\") " +
      "VALUES (?, ?, ?, ?, ?, ?, ?)";
  private static final String INSERT_MARKS_SQL = "INSERT INTO \"STUDENT_MARK\"(\"STMRK_STDN_ID\", \"STMRK_SUBJECT\", " +
      "\"STMRK_MARK\") VALUES (?, ?, ?)";
  private static final String DELETE_STUDENT_SQL = "DELETE FROM \"STUDENT\" WHERE \"STDN_ID\" = ?";
  private static final String UPDATE_STUDENT_SQL = "UPDATE \"STUDENT\" SET \"STDN_UNVR_ID\"=?, \"STDN_NAME\"=?, " +
      "\"STDN_SURNAME\"=?, \"STDN_AGE\"=?, \"STDN_COURSE\"=?, \"STDN_LAST_UPDATE\"=? WHERE \"STDN_ID\"=?";
  private static final String UPDATE_MARK_SQL = "UPDATE \"STUDENT_MARK\" SET \"STMRK_MARK\"=? " +
      "WHERE \"STMRK_STDN_ID\"=? AND \"STMRK_SUBJECT\"=?";
  private static final String SELECT_STUDENT_CREATION_IN_BD_SQL = "SELECT \"STDN_CREATION_IN_DB\" FROM \"STUDENT\" " +
      "WHERE \"STDN_ID\"=?";

  @Override
  public StudentDTO findStudentById(final Long id) {
    final StudentDTO returnedData = jdbcTemplate.query(SELECT_BY_ID_SQL, new StudentExtractor(), id);

    return returnedData;
  }

  @Override
  public StudentDTO create(final StudentDTO studentDTO) throws DaoLayerException {
    StudentDTO result = null;
    final UniversityDTO universityDTO = studentDTO.getUniversityDTO();
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    final LocalDateTime currentDate = LocalDateTime.now();

    try {
      final int numberOfInsertedStudents = jdbcTemplate.update((Connection con) -> {
        PreparedStatement statement = con.prepareStatement(INSERT_STUDENT_SQL, Statement.RETURN_GENERATED_KEYS);
        if (universityDTO != null) {
          statement.setLong(1, universityDTO.getId());
        } else {
          statement.setNull(1, Types.BIGINT);
        }
        statement.setString(2, studentDTO.getName());
        statement.setString(3, studentDTO.getSurname());
        statement.setInt(4, studentDTO.getAge());
        statement.setInt(5, studentDTO.getCourse());
        statement.setTimestamp(6, Timestamp.valueOf(currentDate));
        statement.setTimestamp(7, Timestamp.valueOf(currentDate));

        return statement;
      }, keyHolder);

      final Long resultStudentId = (Long) keyHolder.getKeys().get(STUDENT_ID);

      Set<StudentMarkDTO> marks = studentDTO.getMarks();

      if (marks != null) {
        for (StudentMarkDTO mark : marks) {
          jdbcTemplate.update(INSERT_MARKS_SQL, resultStudentId, mark.getSubject().toString(), mark.getMark());
        }
      }

      if (numberOfInsertedStudents > 0) {
        result = studentDTO;
        result.setId(resultStudentId);
        result.setCreationInBD(currentDate);
        result.setLastUpdateInBD(currentDate);
      }
    } catch (DataIntegrityViolationException e) {
      throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_STUDENT_DATA.getCode());
    }

    return result;
  }

  @Override
  public boolean deleteById(final Long id) {
    boolean result = false;

    final int affectedRowsCount = jdbcTemplate.update(DELETE_STUDENT_SQL, id);

    if (affectedRowsCount > 0) {
      result = true;
    }

    return result;
  }

  @Override
  public StudentDTO update(final StudentDTO studentDTO) throws DaoLayerException {
    StudentDTO result = null;
    final UniversityDTO universityDTO = studentDTO.getUniversityDTO();
    final LocalDateTime updateDateTime = LocalDateTime.now();
    Long universityId = null;

    if (universityDTO != null) {
      universityId = universityDTO.getId();
    }

    try {
      final int affectedRowsCount = jdbcTemplate.update(UPDATE_STUDENT_SQL, universityId, studentDTO.getName(), studentDTO.getSurname(),
          studentDTO.getAge(), studentDTO.getCourse(), Timestamp.valueOf(updateDateTime), studentDTO.getId());

      final List<LocalDateTime> creationInBD = jdbcTemplate.query(SELECT_STUDENT_CREATION_IN_BD_SQL, new CreationInDBRowMapper(),
          studentDTO.getId());

      if (affectedRowsCount > 0 && creationInBD.size() == 1) {
        final Set<StudentMarkDTO> marks = studentDTO.getMarks();

        if (marks != null) {
          for (StudentMarkDTO mark : marks) {
            jdbcTemplate.update(UPDATE_MARK_SQL, mark.getMark(), studentDTO.getId(), mark.getSubject().toString());
          }
        }

        result = studentDTO;
        result.setLastUpdateInBD(updateDateTime);
        result.setCreationInBD(creationInBD.get(0));
      }
    } catch (DataIntegrityViolationException e) {
      throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_STUDENT_DATA.getCode());
    }



    return result;
  }

  @Override
  public List<StudentDTO> findNotAssignedStudents() {
    List<StudentDTO> result = null;

    result = jdbcTemplate.query(SELECT_BY_UNIVERSITY_ID_SQL, new NotAssignedStudentExtractor());

    return result;
  }

  private static class StudentExtractor implements ResultSetExtractor<StudentDTO> {

    @Override
    public StudentDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
      StudentDTO result = null;

      while (rs.next()) {
        Long id = rs.getLong(STUDENT_ID);

        if (id != 0) {
          if (result == null) {
            result = new StudentDTO();
            result.setId(id);

            UniversityDTO universityDTO = null;
            long universityId = rs.getLong(UNIVERSITY_ID);

            if (universityId != 0) {
              universityDTO = new UniversityDTO();
              universityDTO.setId(universityId);
              universityDTO.setName(rs.getString(UNIVERSITY_NAME));
              universityDTO.setDescription(rs.getString(UNIVERSITY_DESCRIPTION));
              universityDTO.setFoundationDate(rs.getTimestamp(UNIVERSITY_FOUNDATION_DATE).toLocalDateTime().toLocalDate());
              universityDTO.setCreationInDB(rs.getTimestamp(UNIVERSITY_CREATION_IN_DB).toLocalDateTime());
              universityDTO.setLastUpdateInDB(rs.getTimestamp(UNIVERSITY_LAST_UPDATE_DB).toLocalDateTime());

              UniversityStatusDTO statusDTO = new UniversityStatusDTO();
              statusDTO.setId(rs.getLong(STATUS_ID));
              statusDTO.setStatusName(UniversityStatus.valueOf(rs.getString(STATUS_NAME)));

              universityDTO.setUniversityStatusDTO(statusDTO);
            }

            result.setUniversityDTO(universityDTO);

            result.setName(rs.getString(STUDENT_NAME));
            result.setSurname(rs.getString(STUDENT_SURNAME));
            result.setAge(rs.getInt(STUDENT_AGE));
            result.setCourse(rs.getInt(STUDENT_COURSE));
            result.setCreationInBD(rs.getTimestamp(STUDENT_CREATION_IN_DB).toLocalDateTime());
            result.setLastUpdateInBD(rs.getTimestamp(STUDENT_LAST_UPDATE_DB).toLocalDateTime());
          }

          final Long markId = rs.getLong(STUDENT_MARK_ID);

          if (markId != 0) {
            Set<StudentMarkDTO> marks = result.getMarks();

            if (marks == null) {
              marks = new HashSet<>();
            }

            StudentMarkDTO mark = new StudentMarkDTO();
            mark.setId(rs.getLong(STUDENT_MARK_ID));
            mark.setSubject(Subject.valueOf(rs.getString(STUDENT_MARK_SUBJECT).toUpperCase()));
            mark.setMark(rs.getInt(STUDENT_MARK_VALUE));

            marks.add(mark);

            result.setMarks(marks);
          }
        }
      }

      return result;
    }
  }

  private static class NotAssignedStudentExtractor implements ResultSetExtractor<List<StudentDTO>> {

    @Override
    public List<StudentDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<StudentDTO> result = null;

      while (rs.next()) {
        final Long studentId = rs.getLong(STUDENT_ID);

        if (studentId != 0) {
          if (result == null) {
            result = new ArrayList<>();
          }

          StudentDTO studentDTO = retrieveStudentById(result, studentId);

          if (studentDTO == null) {
            studentDTO = new StudentDTO();
            studentDTO.setId(studentId);
            studentDTO.setName(rs.getString(STUDENT_NAME));
            studentDTO.setSurname(rs.getString(STUDENT_SURNAME));
            studentDTO.setAge(rs.getInt(STUDENT_AGE));
            studentDTO.setCourse(rs.getInt(STUDENT_COURSE));
            studentDTO.setCreationInBD(rs.getTimestamp(STUDENT_CREATION_IN_DB).toLocalDateTime());
            studentDTO.setLastUpdateInBD(rs.getTimestamp(STUDENT_LAST_UPDATE_DB).toLocalDateTime());
            result.add(studentDTO);
          }

          Set<StudentMarkDTO> marks = studentDTO.getMarks();

          if (marks == null) {
            marks = new HashSet<>();
          }

          final Long markId = rs.getLong(STUDENT_MARK_ID);

          if (markId != 0) {
            StudentMarkDTO mark = new StudentMarkDTO();
            mark.setId(markId);
            mark.setStudentDTO(studentDTO);
            mark.setSubject(Subject.valueOf(rs.getString(STUDENT_MARK_SUBJECT).toUpperCase()));
            mark.setMark(rs.getInt(STUDENT_MARK_VALUE));

            marks.add(mark);

            studentDTO.setMarks(marks);
          }
        }
      }

      return result;
    }

    private StudentDTO retrieveStudentById(final List<StudentDTO> studentDTOs, final Long id) {
      StudentDTO result = null;

      for (StudentDTO studentDTO : studentDTOs) {
        if (id.equals(studentDTO.getId())) {
          result = studentDTO;
          break;
        }
      }

      return result;
    }
  }

  private static class CreationInDBRowMapper implements RowMapper<LocalDateTime> {

    @Override
    public LocalDateTime mapRow(ResultSet rs, int rowNum) throws SQLException {
      LocalDateTime result = null;

      final Timestamp resultTimestamp = rs.getTimestamp(STUDENT_CREATION_IN_DB);

      if (resultTimestamp != null) {
        result = resultTimestamp.toLocalDateTime();
      }

      return result;
    }
  }
}
