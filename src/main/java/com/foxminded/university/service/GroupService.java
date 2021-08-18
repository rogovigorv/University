package com.foxminded.university.service;

import com.foxminded.university.models.Group;
import com.foxminded.university.repository.GroupRepository;
import com.foxminded.university.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void create(Group group) {
        log.info("Create group {}", group);

        try {
            groupRepository.create(group);
        } catch (RepositoryException e) {
            log.warn("Unable to create this group {}", group);
            throw new ServiceException("Unable to create this group.", e);
        }
    }

    public Group getById(int id) {
        log.info("Get group with ID: {}", id);

        Group group;
        try {
            group = groupRepository.getById(id);
        } catch (RepositoryException e) {
            log.warn("Can't get group with ID: {}", id);
            throw new ServiceException("Can't get group with ID " + id + ".", e);
        }

        return group;
    }

    public void update(Group group) {
        log.info("Update group: {} and ID: {}", group, group.getId());

        try {
            groupRepository.update(group);
        } catch (RepositoryException e) {
            log.warn("Unable to update group with ID: {}", group.getId());
            throw new ServiceException("Unable to update group with ID " + group.getId() + ".", e);
        }
    }

    public void delete(int id) {
        log.info("Delete group with ID: {}", id);

        try {
            groupRepository.delete(id);
        } catch (RepositoryException e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new ServiceException("Unable to delete group with ID " + id + ".", e);
        }
    }

    public Page<Group> findPaginated(Pageable pageable) {
        log.info("Get groups pages");

        List<Group> groups = groupRepository.showAll();

        groups.sort(Comparator.comparing(Group::getId));

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
