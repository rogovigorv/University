package com.foxminded.university.repository;

import com.foxminded.university.models.Teacher;
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
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class TeacherRepository implements UniversityRepository<Teacher> {
    private final SessionFactory sessionFactory;

    @Autowired
    public TeacherRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Teacher teacher) throws RepositoryException {
        log.debug("Create teacher: {}", teacher);

        Session currentSession = sessionFactory.openSession();

        try {
            currentSession.save(teacher);
        } catch (DataAccessException e) {
            log.warn("Unable to create this teacher {}", teacher);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Teacher getById(int id) throws RepositoryException {
        log.debug("Get teacher with ID: {}", id);

        Teacher teacher;
        Session currentSession = sessionFactory.openSession();

        try {
            teacher = currentSession.get(Teacher.class, id);
        } catch (DataAccessException e) {
            log.warn("Can't get teacher with ID: {}", id);
            throw new RepositoryException(e);
        }

        return teacher;
    }

    @Override
    public void update(Teacher teacher) throws RepositoryException {
        log.debug("Update teacher: {}", teacher);

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.saveOrUpdate(teacher);
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update teacher with ID: {}", teacher.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete teacher with ID: {}", id);

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.delete(currentSession.byId(Teacher.class).load(id));
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete teacher with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Teacher> showAll() {
        log.debug("Get all teachers");

        Session session = sessionFactory.openSession();
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
            throw new RepositoryException(e);
        }

        return teachers;
    }
}
