package com.epam.aerl.mentoring.dao.hibernate;

import com.epam.aerl.mentoring.dao.StudentDao;
import com.epam.aerl.mentoring.entity.StudentDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("hibernateStudentDaoImpl")
public class HibernateStudentDaoImpl extends HibernateAbstractDao implements StudentDao {
  private static final String INCORRECT_INPUT_DATA_ERR_MSG = "Input data is incorrect. StudentDTO name or surname more than 20 symbols.";
  private static final String CRITERIA_UNIVERSITY_ID_PARAMETER = "universityDTO";

  @Override
  public StudentDTO findStudentById(final Long id) {
    StudentDTO result = null;

    if (id != null) {
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

      final IdentifierLoadAccess<StudentDTO> identifier = sessionFactory.getCurrentSession().byId(StudentDTO.class);
      result = identifier.load(id);
      tx.commit();
    }

    return result;
  }

  @Override
  public StudentDTO create(final StudentDTO studentDTO) throws DaoLayerException {
    StudentDTO result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (studentDTO != null) {
      try {
        studentDTO.setCreationInBD(LocalDateTime.now());
        studentDTO.setLastUpdateInBD(LocalDateTime.now());
        sessionFactory.getCurrentSession().save(studentDTO);
        tx.commit();

        result = studentDTO;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_STUDENT_DATA.getCode());
      }
    }

    return result;
  }

  @Override
  public boolean deleteById(final Long id) {
    boolean result = false;

    if (id != null) {
      StudentDTO founded = findStudentById(id);
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

      if (founded != null) {
        sessionFactory.getCurrentSession().delete(founded);
        tx.commit();
        result = true;
      }
    }

    return result;
  }

  @Override
  public StudentDTO update(final StudentDTO studentDTO) throws DaoLayerException {
    StudentDTO result = null;

    if (findStudentById(studentDTO.getId()) != null) {
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

      try {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.update(studentDTO);
        tx.commit();
        result = studentDTO;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_STUDENT_DATA.getCode());
      }
    }

    return result;
  }

  @Override
  public List<StudentDTO> findNotAssignedStudents() {
    List<StudentDTO> result = null;

    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
    final CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();

    final CriteriaQuery<StudentDTO> criteria = builder.createQuery(StudentDTO.class);
    Root<StudentDTO> root = criteria.from(StudentDTO.class);
    criteria.select(root);
    criteria.where(builder.isNull(root.get(CRITERIA_UNIVERSITY_ID_PARAMETER)));

    List resultList = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();

    if (CollectionUtils.isNotEmpty(resultList)) {
      result = resultList;
    }

    return result;
  }
}
