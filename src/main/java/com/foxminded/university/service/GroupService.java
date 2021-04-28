package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void create(Group group) {
        groupDao.create(group);
    }

    public Group getById(int id) {
        return groupDao.getById(id);
    }

    public void update(Group group, int id) {
        groupDao.update(group, id);
    }

    public void delete(int id) {
        groupDao.delete(id);
    }
}
