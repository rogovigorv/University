package com.foxminded.university.repository;

import com.foxminded.university.models.Group;
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
public class GroupRepository implements UniversityRepository<Group> {

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public GroupRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(Group group) {
        log.debug("Create group: {}", group);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(group);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to create this group {}", group);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Group getById(int id) {
        log.debug("Get group with ID: {}", id);

        Group group;
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            group = entityManager.find(Group.class, id);
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Can't get group with ID: {}", id);
            throw new RepositoryException(e);
        }

        return group;
    }

    @Override
    public void update(Group group) throws RepositoryException {
        log.debug("Update group: {} and ID: {}", group, group.getId());

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.merge(group);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update group with ID: {}", group.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete group with ID: {}", id);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Group group = entityManager.find(Group.class, id);

        try {
            entityManager.getTransaction().begin();
            entityManager.remove(group);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Group> showAll() throws RepositoryException {
        log.info("Get all groups");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Group> groups = new ArrayList<>();

        try {
            List<?> results = entityManager.createQuery("Select g from Group g").getResultList();
            results.forEach(r -> {
                if (r != null) {
                    groups.add((Group) r);
                }
            });
            entityManager.close();
        } catch (DataAccessException e) {
            log.warn("Unable to get all groups");
            throw new RepositoryException(e);
        }

        return groups;
    }
}
