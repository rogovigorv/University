package com.foxminded.university.dao;

import com.foxminded.university.models.Lecture;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class LectureDao implements UniversityDao<Lecture> {
    private final SessionFactory sessionFactory;

    @Autowired
    public LectureDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Lecture lecture) throws DaoException {
        log.debug("Create lecture: {}", lecture);

        Session currentSession = sessionFactory.openSession();

        try {
            currentSession.save(lecture);
        } catch (DataAccessException e) {
            log.warn("Unable to create this lecture {}", lecture);
            throw new DaoException(e);
        }
    }

    @Override
    public Lecture getById(int id) throws DaoException {
        log.debug("Get lecture with ID: {}", id);

        Lecture lecture;
        Session currentSession = sessionFactory.openSession();

        try {
            lecture = currentSession.get(Lecture.class, id);
        } catch (DataAccessException e) {
            log.warn("Can't get lecture with ID: {}", id);
            throw new DaoException(e);
        }

        return lecture;
    }

    @Override
    public void update(Lecture lecture) throws DaoException {
        log.debug("Update lecture: {}", lecture);

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.saveOrUpdate(lecture);
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update lecture with ID: {}", lecture.getId());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete lecture with ID: {}", id);

        Session session = sessionFactory.openSession();

        try {
            session.delete(session.byId(Lecture.class).load(id));
        } catch (DataAccessException e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Lecture> showAll() throws DaoException {
        log.debug("Get all lectures");

        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        List<Lecture> lectures = new ArrayList<>();

        try {
            CriteriaQuery<Lecture> criteriaQuery = criteriaBuilder.createQuery(Lecture.class);
            Root<Lecture> root = criteriaQuery.from(Lecture.class);
            criteriaQuery.select(root);
            Query query = session.createQuery(criteriaQuery);
            List<?> results = query.getResultList();
            results.forEach(r -> {
                if(r != null) {
                    lectures.add((Lecture) r);
                }
            });
        } catch (DataAccessException e) {
            log.warn("Unable to get all lectures");
            throw new DaoException(e);
        }

        return lectures;
    }
}
