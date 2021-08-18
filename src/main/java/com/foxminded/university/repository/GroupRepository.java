package com.foxminded.university.repository;

import com.foxminded.university.models.Group;
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
public class GroupRepository implements UniversityRepository<Group> {
    private final SessionFactory sessionFactory;

    @Autowired
    public GroupRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Group group) {
        log.debug("Create group: {}", group);

        Session currentSession = sessionFactory.openSession();

        try {
            currentSession.save(group);
        } catch (DataAccessException e) {
            log.warn("Unable to create this group {}", group);
            throw new RepositoryException(e);
        }
    }

    @Override
    public Group getById(int id) {
        log.debug("Get group with ID: {}", id);

        Group group;
        Session currentSession = sessionFactory.openSession();

        try {
            group = currentSession.get(Group.class, id);
        } catch (DataAccessException e) {
            log.warn("Can't get group with ID: {}", id);
            throw new RepositoryException(e);
        }

        return group;
    }

    @Override
    public void update(Group group) throws RepositoryException {
        log.debug("Update group: {} and ID: {}", group, group.getId());

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.update(group);
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to update group with ID: {}", group.getId());
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(int id) throws RepositoryException {
        log.debug("Delete group with ID: {}", id);

        Session currentSession = sessionFactory.openSession();
        Transaction transaction = currentSession.beginTransaction();

        try {
            currentSession.delete(currentSession.byId(Group.class).load(id));
            transaction.commit();
            currentSession.close();
        } catch (DataAccessException e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<Group> showAll() throws RepositoryException {
        log.info("Get all groups");

        List<Group> groups;
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        groups = new ArrayList<>();

        try {
            CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
            Root<Group> root = criteriaQuery.from(Group.class);
            criteriaQuery.select(root);
            Query query = session.createQuery(criteriaQuery);
            List<?> results = query.getResultList();
            results.forEach(r -> {
                if (r != null) {
                    groups.add((Group) r);
                }
            });
        } catch (DataAccessException e) {
            log.warn("Unable to get all groups");
            throw new RepositoryException(e);
        }

        return groups;

    }
}
