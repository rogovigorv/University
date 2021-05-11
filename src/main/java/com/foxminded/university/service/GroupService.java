package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GroupService {
    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void create(Group group) {
        log.info("GroupService create method started");

        try {
            groupDao.create(group);
        } catch (DaoException e) {
            throw new ServiceException("Unable to create this group.", e);
        }
    }

    public Group getById(int id) {
        log.info("GroupService getById method started");

        Group group;
        try {
            group = groupDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't get group with ID " + id + ".", e);
        }

        return group;
    }

    public void update(Group group, int id) {
        log.info("GroupService update method started");

        try {
            groupDao.update(group, id);
        } catch (DaoException e) {
            throw new ServiceException("Unable to update group with ID " + id + ".", e);
        }
    }

    public void delete(int id) {
        log.info("GroupService delete method started");

        try {
            groupDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Unable to delete group with ID " + id + ".", e);
        }
    }
}
