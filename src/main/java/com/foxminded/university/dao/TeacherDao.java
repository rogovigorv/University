package com.foxminded.university.dao;

import com.foxminded.university.models.Teacher;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class TeacherDao implements UniversityDao<Teacher> {
    private final SessionFactory sessionFactory;

    @Autowired
    public TeacherDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Teacher teacher) throws DaoException {
        log.debug("Create teacher: {}", teacher);

        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.save(teacher);
        } catch (DataAccessException e) {
            log.warn("Unable to create this teacher {}", teacher);
            throw new DaoException(e);
        }
    }

    @Override
    public Teacher getById(int id) throws DaoException {
        log.debug("Get teacher with ID: {}", id);

        Teacher teacher;
        Session currentSession = sessionFactory.getCurrentSession();

        try {
            teacher = currentSession.get(Teacher.class, id);
        } catch (DataAccessException e) {
            log.warn("Can't get teacher with ID: {}", id);
            throw new DaoException(e);
        }

        return teacher;
    }

    @Override
    public void update(Teacher teacher) throws DaoException {
        log.debug("Update teacher: {}", teacher);

        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.saveOrUpdate(teacher);
        } catch (DataAccessException e) {
            log.warn("Unable to update teacher with ID: {}", teacher.getId());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete teacher with ID: {}", id);

        Session session = sessionFactory.getCurrentSession();

        try {
            session.delete(session.byId(Teacher.class).load(id));
        } catch (DataAccessException e) {
            log.warn("Unable to delete teacher with ID: {}", id);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Teacher> showAll() {
        log.debug("Get all teachers");

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        List<Teacher> teachers = new ArrayList<>();

        try {
            CriteriaQuery<Teacher> criteriaQuery = criteriaBuilder.createQuery(Teacher.class);
            Root<Teacher> root = criteriaQuery.from(Teacher.class);
            criteriaQuery.select(root);
            Query query = session.createQuery(criteriaQuery);
            List<?> results = query.getResultList();
            results.forEach(r -> {
                if(r != null) {
                    teachers.add((Teacher) r);
                }
            });
        } catch (DataAccessException e) {
            log.warn("Unable to get all teachers");
            throw new DaoException(e);
        }

        return teachers;
    }
}
