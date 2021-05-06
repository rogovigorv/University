package com.foxminded.university.service;

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
        groupDao.create(group);
    }

    public Group getById(int id) {
        log.info("GroupService getById method started");
        return groupDao.getById(id);
    }

    public void update(Group group, int id) {
        log.info("GroupService update method started");
        groupDao.update(group, id);
    }

    public void delete(int id) {
        log.info("GroupService delete method started");
        groupDao.delete(id);
    }
}
