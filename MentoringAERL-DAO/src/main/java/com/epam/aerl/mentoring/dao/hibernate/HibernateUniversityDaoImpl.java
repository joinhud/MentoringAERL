package com.epam.aerl.mentoring.dao.hibernate;

import com.epam.aerl.mentoring.dao.UniversityDao;
import com.epam.aerl.mentoring.entity.UniversityDTO;
import com.epam.aerl.mentoring.exception.DaoLayerException;
import com.epam.aerl.mentoring.type.ErrorMessage;
import com.epam.aerl.mentoring.type.UniversityStatus;
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

@Repository("hibernateUniversityDaoImpl")
public class HibernateUniversityDaoImpl extends HibernateAbstractDao implements UniversityDao {
  private static final String INCORRECT_INPUT_DATA_ERR_MSG = "Input data is incorrect. UniversityDTO name more than " +
      "15 symbols or university description more than 300 symbols.";
  private static final String CRITERIA_STATUS_DTO_PARAMETER = "universityStatusDTO";
  private static final String CRITERIA_STATUS_NAME_PARAMETER = "statusName";

  @Override
  public UniversityDTO create(final UniversityDTO universityDTO) throws DaoLayerException {
    UniversityDTO result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (universityDTO != null) {
      try {
        universityDTO.setCreationInDB(LocalDateTime.now());
        universityDTO.setLastUpdateInDB(LocalDateTime.now());
        sessionFactory.getCurrentSession().save(universityDTO);
        tx.commit();

        result = universityDTO;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_UNIVERSITY_DATA.getCode());
      }
    }

    return result;
  }

  @Override
  public UniversityDTO findById(final Long id) {
    UniversityDTO result = null;

    if (id != null) {
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
      final IdentifierLoadAccess<UniversityDTO> identifier = sessionFactory.getCurrentSession().byId(UniversityDTO.class);
      result = identifier.load(id);
      tx.commit();
    }

    return result;
  }

  @Override
  public UniversityDTO update(final UniversityDTO universityDTO) throws DaoLayerException {
    UniversityDTO result = null;
    final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();

    if (universityDTO != null) {
      try {
        Session session = sessionFactory.getCurrentSession();
        session.clear();
        session.update(universityDTO);
        tx.commit();

        result = universityDTO;
      } catch (PersistenceException e) {
        tx.rollback();
        throw new DaoLayerException(INCORRECT_INPUT_DATA_ERR_MSG, e, ErrorMessage.NO_VALID_UNIVERSITY_DATA.getCode());
      }
    }

    return result;
  }

  @Override
  public List<UniversityDTO> findUniversitiesByStatus(final UniversityStatus status) {
    List<UniversityDTO> result = null;

    if (status != null) {
      final Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
      CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();

      CriteriaQuery<UniversityDTO> criteria = builder.createQuery(UniversityDTO.class);
      Root<UniversityDTO> root = criteria.from(UniversityDTO.class);
      criteria.select(root);
      criteria.where(builder.equal(root.get(CRITERIA_STATUS_DTO_PARAMETER).get(CRITERIA_STATUS_NAME_PARAMETER), status));

      List resultList = sessionFactory.getCurrentSession().createQuery(criteria).getResultList();


      if (CollectionUtils.isNotEmpty(resultList)) {
        result = resultList;
      }

      tx.commit();
    }

    return result;
  }
}
