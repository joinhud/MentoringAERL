package com.epam.aerl.mentoring.dao.jdbc;

import com.epam.aerl.mentoring.dao.UniversityDao;
import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.entity.StudentMarkDTO;
import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.entity.UniversityStatusDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.Subject;
import com.epam.aerl.mentoring.type.UniversityStatus;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("universityDaoImpl")
public class UniversityDaoImpl extends AbstractDao implements UniversityDao {
  private static final String UNIVERSITY_ID = "UNVR_ID";
  private static final String UNIVERSITY_NAME = "UNVR_NAME";
  private static final String UNIVERSITY_DESCRIPTION = "UNVR_DESCRIPTION";
  private static final String UNIVERSITY_FOUNDATION = "UNVR_FOUNDATION_DATE";
  private static final String UNIVERSITY_CREATION_IN_DB = "UNVR_CREATION_IN_DB";
  private static final String UNIVERSITY_LAST_UPDATE = "UNVR_LAST_UPDATE";
  private static final String STUDENT_ID = "STDN_ID";
  private static final String STUDENT_NAME = "STDN_NAME";
  private static final String STUDENT_SURNAME = "STDN_SURNAME";
  private static final String STUDENT_AGE = "STDN_AGE";
  private static final String STUDENT_COURSE = "STDN_COURSE";
  private static final String STUDENT_CREATION_IN_DB = "STDN_CREATION_IN_DB";
  private static final String STUDENT_LAST_UPDATE_DB = "STDN_LAST_UPDATE";
  private static final String STUDENT_MARK_ID = "STMRK_ID";
  private static final String STUDENT_MARK_SUBJECT = "STMRK_SUBJECT";
  private static final String STUDENT_MARK_VALUE = "STMRK_MARK";
  private static final String STATUS_ID = "STAT_ID";
  private static final String STATUS_NAME = "STAT_NAME";

  private static final String INCORRECT_INPUT_DATA_ERR_MSG = "Input data is incorrect. UniversityDTO name more than " +
      "15 symbols or university description more than 300 symbols.";

  private static final String INSERT_UNIVERSITY_SQL = "INSERT INTO \"UNIVERSITY\"(\"UNVR_NAME\", \"UNVR_DESCRIPTION\", " +
      "\"UNVR_FOUNDATION_DATE\", \"UNVR_CREATION_IN_DB\", \"UNVR_LAST_UPDATE\", \"UNVR_STAT_ID\") " +
      "VALUES (?, ?, ?, ?, ?, ?)";
  private static final String SELECT_UNIVERSITY_BY_ID_SQL = "SELECT \"UNVR_ID\", \"UNVR_NAME\", \"UNVR_DESCRIPTION\", " +
      "\"UNVR_FOUNDATION_DATE\", \"UNVR_CREATION_IN_DB\", \"UNVR_LAST_UPDATE\", \"STAT_ID\", \"STAT_NAME\" " +
      "FROM \"UNIVERSITY\" JOIN \"UNIVERSITY_STATUS\" ON \"UNVR_STAT_ID\" = \"STAT_ID\" " +
      "WHERE \"UNVR_ID\"=?";
  private static final String SELECT_UNIVERSITIES_BY_STATUS_SQL = "SELECT \"UNVR_ID\", \"UNVR_NAME\", \"UNVR_DESCRIPTION\", " +
      "\"UNVR_FOUNDATION_DATE\", \"UNVR_CREATION_IN_DB\", \"UNVR_LAST_UPDATE\", \"STAT_ID\", \"STAT_NAME\" " +
      "FROM \"UNIVERSITY\" JOIN \"UNIVERSITY_STATUS\" ON \"UNVR_STAT_ID\" = \"STAT_ID\" " +
      "WHERE \"STAT_NAME\"=?";
  private static final String SELECT_STUDENTS_BY_ID_SQL = "SELECT \"STDN_ID\", \"STDN_UNVR_ID\", \"STDN_NAME\", \"STDN_SURNAME\", " +
      "\"STDN_AGE\", \"STDN_COURSE\", \"STDN_CREATION_IN_DB\", \"STDN_LAST_UPDATE\", \"STMRK_ID\", \"STMRK_SUBJECT\", " +
      "\"STMRK_MARK\" " +
      "FROM \"STUDENT\" LEFT JOIN \"STUDENT_MARK\" ON \"STDN_ID\" = \"STMRK_STDN_ID\" " +
      "WHERE \"STDN_UNVR_ID\"=?";
  private static final String UPDATE_UNIVERSITY_SQL = "UPDATE \"UNIVERSITY\" SET \"UNVR_NAME\"=?, \"UNVR_DESCRIPTION\"=?, " +
      "\"UNVR_FOUNDATION_DATE\"=?, \"UNVR_LAST_UPDATE\"=?, \"UNVR_STAT_ID\"=? WHERE \"UNVR_ID\"=?";


  @Override
  public UniversityDTO create(final UniversityDTO universityDTO) throws DaoLayerException {
    UniversityDTO result = null;
    final LocalDateTime now = LocalDateTime.now();
    final KeyHolder keyHolder = new GeneratedKeyHolder();
    final UniversityStatusDTO status;

    if (universityDTO != null) {
      if (universityDTO.getName() != null) {
        try {
          if (universityDTO.getUniversityStatusDTO() == null) {
            status = new UniversityStatusDTO();
            status.setId((long) UniversityStatus.PENDING_GOVERNMENT_APPROVAL.ordinal() + 1);
            status.setStatusName(UniversityStatus.PENDING_GOVERNMENT_APPROVAL);
          } else {
            status = universityDTO.getUniversityStatusDTO();
          }

          final int affectedRowsCount = jdbcTemplate.update((Connection con) -> {
            PreparedStatement statement = con.prepareStatement(INSERT_UNIVERSITY_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, universityDTO.getName());
            statement.setString(2, universityDTO.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(universityDTO.getFoundationDate().atStartOfDay()));
            statement.setTimestamp(4, Timestamp.valueOf(now));
            statement.setTimestamp(5, Timestamp.valueOf(now));
            statement.setLong(6, status.getId());

            return statement;
          }, keyHolder);

          final Long resultUniversityId = (Long) keyHolder.getKeys().get(UNIVERSITY_ID);

          if (affectedRowsCount > 0) {
            result = universityDTO;
            result.setUniversityStatusDTO(status);
            result.setCreationInDB(now);
            result.setLastUpdateInDB(now);
            result.setId(resultUniversityId);
          }
        } catch (DataIntegrityViolationException e) {
          throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_UNIVERSITY_DATA.getCode());
        }
      }
    }

    return result;
  }

  @Override
  public UniversityDTO findById(final Long id) {
    final List<UniversityDTO> returnedData = jdbcTemplate.query(SELECT_UNIVERSITY_BY_ID_SQL, new UniversityMapper(), id);
    UniversityDTO result = null;

    if (1 == returnedData.size()) {
      result = returnedData.get(0);

      final Set<StudentDTO> studentDTOS = jdbcTemplate.query(SELECT_STUDENTS_BY_ID_SQL, new StudentExtractor(), id);

      if (studentDTOS != null) {
        final UniversityDTO universityDTO = result;

        studentDTOS.forEach(student -> student.setUniversityDTO(universityDTO));
        result.setStudentDTOs(studentDTOS);
      }
    }

    return result;
  }

  @Override
  public UniversityDTO update(final UniversityDTO universityDTO) throws DaoLayerException {
    UniversityDTO result = null;

    try {
      if (universityDTO != null) {
        final Long universityId = universityDTO.getId();

        if (universityId != null) {
          UniversityStatusDTO status = universityDTO.getUniversityStatusDTO();

          if (status == null) {
            status = new UniversityStatusDTO();
            status.setId((long) UniversityStatus.PENDING_GOVERNMENT_APPROVAL.ordinal() + 1);
            status.setStatusName(UniversityStatus.PENDING_GOVERNMENT_APPROVAL);
          }

          final int affectedRowsCount = jdbcTemplate.update(UPDATE_UNIVERSITY_SQL, universityDTO.getName(), universityDTO.getDescription(),
              universityDTO.getFoundationDate(), Timestamp.valueOf(LocalDateTime.now()), status.getId(), universityDTO.getId());

          if (affectedRowsCount > 0) {
            result = findById(universityDTO.getId());
          }
        }
      }
    } catch (DataIntegrityViolationException e) {
      throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_UNIVERSITY_DATA.getCode());
    }

    return result;
  }

  @Override
  public List<UniversityDTO> findUniversitiesByStatus(final UniversityStatus status) {
    List<UniversityDTO> result = null;

    if (status != null) {
      final List<UniversityDTO> returnedData = jdbcTemplate.query(SELECT_UNIVERSITIES_BY_STATUS_SQL, new UniversityMapper(), status.toString());

      if (CollectionUtils.isNotEmpty(returnedData)) {
        for (UniversityDTO universityDTO : returnedData) {
          final Set<StudentDTO> studentDTOS = jdbcTemplate.query(SELECT_STUDENTS_BY_ID_SQL, new StudentExtractor(), universityDTO.getId());

          if (studentDTOS != null) {
            studentDTOS.forEach(student -> student.setUniversityDTO(universityDTO));
            universityDTO.setStudentDTOs(studentDTOS);
          }
        }

        result = returnedData;
      }
    }

    return result;
  }

  private static class UniversityMapper implements RowMapper<UniversityDTO> {
    @Override
    public UniversityDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
      UniversityDTO universityDTO = new UniversityDTO();
      universityDTO.setId(rs.getLong(UNIVERSITY_ID));
      universityDTO.setName(rs.getString(UNIVERSITY_NAME));
      universityDTO.setDescription(rs.getString(UNIVERSITY_DESCRIPTION));
      universityDTO.setFoundationDate(rs.getTimestamp(UNIVERSITY_FOUNDATION).toLocalDateTime().toLocalDate());
      universityDTO.setCreationInDB(rs.getTimestamp(UNIVERSITY_CREATION_IN_DB).toLocalDateTime());
      universityDTO.setLastUpdateInDB(rs.getTimestamp(UNIVERSITY_LAST_UPDATE).toLocalDateTime());

      UniversityStatusDTO statusDTO = new UniversityStatusDTO();
      statusDTO.setId(rs.getLong(STATUS_ID));
      statusDTO.setStatusName(UniversityStatus.valueOf(rs.getString(STATUS_NAME)));
      universityDTO.setUniversityStatusDTO(statusDTO);

      return universityDTO;
    }
  }

  private static class StudentExtractor implements ResultSetExtractor<Set<StudentDTO>> {

    @Override
    public Set<StudentDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
      Set<StudentDTO> result = null;

      while (rs.next()) {
        final Long id = rs.getLong(STUDENT_ID);

        if (id != 0) {
          if (result == null) {
            result = new HashSet<>();
          }

          StudentDTO studentDTO = retrieveStudentById(result, id);

          if (studentDTO == null) {
            studentDTO = new StudentDTO();
            studentDTO.setId(id);
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

    private StudentDTO retrieveStudentById(final Set<StudentDTO> studentDTOS, final Long id) {
      StudentDTO result = null;

      for (StudentDTO studentDTO : studentDTOS) {
        if (id.equals(studentDTO.getId())) {
          result = studentDTO;
          break;
        }
      }

      return result;
    }
  }
}
