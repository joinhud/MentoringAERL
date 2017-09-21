package com.epam.aerl.mentoring.dao.hibernate;

import com.epam.aerl.mentoring.dao.StudentDao;
import com.epam.aerl.mentoring.entity.Student;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import javax.persistence.PersistenceException;

@Repository("hibernateStudentDaoImpl")
public class HibernateStudentDaoImpl extends HibernateAbstractDao implements StudentDao {
  private static final String INCORRECT_INPUT_DATA_ERR_MSG = "Input data is incorrect. Student name or surname more than 20 symbols.";

  @Override
  public Student findStudentById(final Long id) {
    Student result = null;

    if (id != null) {
      Transaction tx = sessionFactory.getCurrentSession().getTransaction();

      if (tx == null) {
        tx = sessionFactory.getCurrentSession().beginTransaction();
      }

      final IdentifierLoadAccess<Student> identifier = sessionFactory.getCurrentSession().byId(Student.class);
      result = identifier.load(id);
      tx.commit();
    }

    return result;
  }

  @Override
  public Student create(final Student student) throws DaoLayerException {
    Student result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (student != null) {
      try {
        student.setCreationInBD(LocalDateTime.now());
        student.setLastUpdateInBD(LocalDateTime.now());
        sessionFactory.getCurrentSession().save(student);
        tx.commit();

        result = student;
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
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
      Student founded = findStudentById(id);

      if (founded != null) {
        sessionFactory.getCurrentSession().delete(founded);
        tx.commit();
        result = true;
      }
    }

    return result;
  }

  @Override
  public Student update(final Student student) throws DaoLayerException {
    Student result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (findStudentById(student.getId()) != null) {
      try {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.update(student);
        tx.commit();
        result = student;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_STUDENT_DATA.getCode());
      }
    }

    return result;
  }
}
