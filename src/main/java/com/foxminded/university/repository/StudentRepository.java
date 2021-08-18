package com.foxminded.university.repository;

import com.foxminded.university.models.Student;
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
public class StudentRepository implements UniversityRepository<Student> {
    private final SessionFactory sessionFactory;

    @Autowired
    public StudentRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Student student) throws RepositoryException {
        log.debug("Create student: {}", student);

        Session currentSession = sessionFactory.openSession();

        try {
            currentSession.save(student);
        } catch (DataAccessException e) {
            log.warn("Unable to create this student {}", student);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Student getById(int id) throws RepositoryException {
        log.debug("Get student with ID: {}", id);

        Student student;
        Session currentSession = sessionFactory.openSession();

        try {
            student = currentSession.get(Student.class, id);
        } catch (DataAccessException e) {
            log.warn("Can't get student with ID: {}", id);
            throw new RepositoryException(e);
        }

        return student;
    }

    @Override
    public void update(Student student) throws RepositoryException {
        log.debug("Update student: {}", student);

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.saveOrUpdate(student);
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update student with ID: {}", student.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete student with ID: {}", id);

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.delete(currentSession.byId(Student.class).load(id));
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete student with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Student> showAll() throws RepositoryException {
        log.debug("Get all students");

        Session session = sessionFactory.openSession();
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
            throw new RepositoryException(e);
        }

        return students;
    }
}
