package com.epam.aerl.mentoring.dao.hibernate;

import com.epam.aerl.mentoring.dao.UniversityDao;
import com.epam.aerl.mentoring.entity.University;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import javax.persistence.PersistenceException;

@Repository("hibernateUniversityDaoImpl")
public class HibernateUniversityDaoImpl extends HibernateAbstractDao implements UniversityDao {
  private static final String INCORRECT_INPUT_DATA_ERR_MSG = "Input data is incorrect. University name more than " +
      "15 symbols or university description more than 300 symbols.";

  @Override
  public University create(final University university) throws DaoLayerException {
    University result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (university != null) {
      try {
        university.setCreationInDB(LocalDateTime.now());
        university.setLastUpdateInDB(LocalDateTime.now());
        sessionFactory.getCurrentSession().save(university);
        tx.commit();

        result = university;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_UNIVERSITY_DATA.getCode());
      }
    }

    return result;
  }

  @Override
  public University findById(final Long id) {
    University result = null;

    if (id != null) {
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
      final IdentifierLoadAccess<University> identifier = sessionFactory.getCurrentSession().byId(University.class);
      result = identifier.load(id);
      tx.commit();
    }

    return result;
  }

  @Override
  public University update(final University university) throws DaoLayerException {
    University result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (university != null) {
      try {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.update(university);
        tx.commit();

        result = university;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_UNIVERSITY_DATA.getCode());
      }
    }

    return result;
  }
}
