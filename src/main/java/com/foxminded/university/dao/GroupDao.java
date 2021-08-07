package com.foxminded.university.dao;

import com.foxminded.university.models.Group;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class GroupDao implements UniversityDao<Group> {
    private final SessionFactory sessionFactory;

    @Autowired
    public GroupDao(SessionFactory sessionFactory) {
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
            throw new DaoException(e);
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
            throw new DaoException(e);
        }

        return group;
    }

    @Override
    public void update(Group group) throws DaoException {
        log.debug("Update group: {} and ID: {}", group, group.getId());

        Session currentSession = sessionFactory.getCurrentSession();

        try {
            currentSession.update(group);
        } catch (DataAccessException e) {
            log.warn("Unable to update group with ID: {}", group.getId());
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete group with ID: {}", id);

        Session session = sessionFactory.getCurrentSession();

        try {
            session.delete(session.byId(Group.class).load(id));
        } catch (DataAccessException e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Group> showAll() throws DaoException {
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
            throw new DaoException(e);
        }

        return groups;

    }
}
