package com.foxminded.university.repository;

import com.foxminded.university.models.Teacher;
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
public class TeacherRepository implements UniversityRepository<Teacher> {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public TeacherRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(Teacher teacher) throws RepositoryException {
        log.debug("Create teacher: {}", teacher);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(teacher);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to create this teacher {}", teacher);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Teacher getById(int id) throws RepositoryException {
        log.debug("Get teacher with ID: {}", id);

        Teacher teacher;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            teacher = entityManager.find(Teacher.class, id);
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Can't get teacher with ID: {}", id);
            throw new RepositoryException(e);
        }

        return teacher;
    }

    @Override
    public void update(Teacher teacher) throws RepositoryException {
        log.debug("Update teacher: {}", teacher);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(teacher);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update teacher with ID: {}", teacher.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete teacher with ID: {}", id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Teacher teacher = entityManager.find(Teacher.class, id);

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(teacher);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete teacher with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Teacher> showAll() {
        log.debug("Get all teachers");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Teacher> teachers = new ArrayList<>();

        try {
            List<?> results = entityManager.createQuery("Select t from Teacher t").getResultList();
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
