package com.foxminded.university.service;

import com.foxminded.university.dao.DaoException;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        log.info("Create group {}", group);

        try {
            groupDao.create(group);
        } catch (DaoException e) {
            log.warn("Unable to create this group {}", group);
            throw new ServiceException("Unable to create this group.", e);
        }
    }

    public Group getById(int id) {
        log.info("Get group with ID: {}", id);

        Group group;
        try {
            group = groupDao.getById(id);
        } catch (DaoException e) {
            log.warn("Can't get group with ID: {}", id);
            throw new ServiceException("Can't get group with ID " + id + ".", e);
        }

        return group;
    }

    public void update(Group group, int id) {
        log.info("Update group: {} and ID: {}", group, id);

        try {
            groupDao.update(group, id);
        } catch (DaoException e) {
            log.warn("Unable to update group with ID: {}", id);
            throw new ServiceException("Unable to update group with ID " + id + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete group with ID: {}", id);

        try {
            groupDao.delete(id);
        } catch (DaoException e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new ServiceException("Unable to delete group with ID " + id + ".", e);
        }
    }

    public Page<Group> findPaginated(Pageable pageable) {
        log.debug("Get groups pages");

        List<Group> groups = groupDao.showAll();

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Group> currentPageList;

        if (groups.size() < startItem) {
            currentPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, groups.size());
            currentPageList = groups.subList(startItem, toIndex);
        }

        Page<Group> groupPage =
                new PageImpl<>(currentPageList, PageRequest.of(currentPage, pageSize), groups.size());

        return groupPage;
    }
}