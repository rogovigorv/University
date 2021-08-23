package com.foxminded.university.repository;

import com.foxminded.university.models.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class StudentRepository implements UniversityRepository<Student> {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public StudentRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(Student student) throws RepositoryException {
        log.debug("Create student: {}", student);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(student);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to create this student {}", student);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Student getById(int id) throws RepositoryException {
        log.debug("Get student with ID: {}", id);

        Student student;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            student = entityManager.find(Student.class, id);
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Can't get student with ID: {}", id);
            throw new RepositoryException(e);
        }

        return student;
    }

    @Override
    public void update(Student student) throws RepositoryException {
        log.debug("Update student: {}", student);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(student);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update student with ID: {}", student.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete student with ID: {}", id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Student student = entityManager.find(Student.class, id);

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(student);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete student with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Student> showAll() throws RepositoryException {
        log.debug("Get all students");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Student> students = new ArrayList<>();

        try {
            List<?> results = entityManager.createQuery("Select s from Student s").getResultList();
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
