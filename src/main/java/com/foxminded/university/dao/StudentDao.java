package com.foxminded.university.dao;

import com.foxminded.university.models.Student;
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
public class StudentDao implements UniversityDao<Student>{
    private final SessionFactory sessionFactory;

    @Autowired
    public StudentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Student student) throws DaoException {
        log.debug("Create student: {}", student);

        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.save(student);
        } catch (DataAccessException e) {
            log.warn("Unable to create this student {}", student);
            throw new DaoException(e);
        }
    }

    @Override
    public Student getById(int id) throws DaoException {
        log.debug("Get student with ID: {}", id);

        Student student;
        Session currentSession = sessionFactory.getCurrentSession();

        try {
            student = currentSession.get(Student.class, id);
        } catch (DataAccessException e) {
            log.warn("Can't get student with ID: {}", id);
            throw new DaoException(e);
        }

        return student;
    }

    @Override
    public void update(Student student) throws DaoException {
        log.debug("Update student: {}", student);

        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.saveOrUpdate(student);
        } catch (DataAccessException e) {
            log.warn("Unable to update student with ID: {}", student.getId());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete student with ID: {}", id);

        Session session = sessionFactory.getCurrentSession();

        try {
            session.delete(session.byId(Student.class).load(id));
        } catch (DataAccessException e) {
            log.warn("Unable to delete student with ID: {}", id);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Student> showAll() throws DaoException {
        log.debug("Get all students");

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        List<Student> students = new ArrayList<>();

        try {
            CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
            Root<Student> root = criteriaQuery.from(Student.class);
            criteriaQuery.select(root);
            Query query = session.createQuery(criteriaQuery);
            List<?> results = query.getResultList();
            results.forEach(r -> {
                if(r != null) {
                    students.add((Student) r);
                }
            });
        } catch (DataAccessException e) {
            log.warn("Unable to get all students");
            throw new DaoException(e);
        }

        return students;
    }
}
