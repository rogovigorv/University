package com.foxminded.university.repository;

import com.foxminded.university.models.Lecture;
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
public class LectureRepository implements UniversityRepository<Lecture> {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public LectureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(Lecture lecture) throws RepositoryException {
        log.debug("Create lecture: {}", lecture);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(lecture);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to create this lecture {}", lecture);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Lecture getById(int id) throws RepositoryException {
        log.debug("Get lecture with ID: {}", id);

        Lecture lecture;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            lecture = entityManager.find(Lecture.class, id);
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Can't get lecture with ID: {}", id);
            throw new RepositoryException(e);
        }

        return lecture;
    }

    @Override
    public void update(Lecture lecture) throws RepositoryException {
        log.debug("Update lecture: {}", lecture);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(lecture);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update lecture with ID: {}", lecture.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete lecture with ID: {}", id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Lecture lecture = entityManager.find(Lecture.class, id);

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(lecture);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete lecture with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Lecture> showAll() throws RepositoryException {
        log.debug("Get all lectures");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Lecture> lectures = new ArrayList<>();

        try {
            List<?> results = entityManager.createQuery("Select l from Lecture l").getResultList();
            results.forEach(r -> {
                if(r != null) {
                    lectures.add((Lecture) r);
                }
            });
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to get all lectures");
            throw new RepositoryException(e);
        }

        return lectures;
    }
}
