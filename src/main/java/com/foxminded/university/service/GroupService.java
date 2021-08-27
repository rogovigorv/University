package com.foxminded.university.service;

import com.foxminded.university.models.Group;
import com.foxminded.university.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
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
            groupRepository.save(group);
        } catch (Throwable e) {
            log.warn("Unable to create this group {}", group);
            throw new ServiceException("Unable to create this group.", e);
        }
    }

    public Group getById(long id) {
        log.info("Get group with ID: {}", id);

        Group group;
        try {
            group = groupRepository.getOne(id);
        } catch (Throwable e) {
            log.warn("Can't get group with ID: {}", id);
            throw new ServiceException("Can't get group with ID " + id + ".", e);
        }

        return group;
    }

    public void update(Group group) {
        log.info("Update group: {} and ID: {}", group, group.getId());

        try {
            Group groupToUpdate = groupRepository.getOne(group.getId());
            groupToUpdate.setGroupName(group.getGroupName());
            groupRepository.save(groupToUpdate);
        } catch (Throwable e) {
            log.warn("Unable to update group with ID: {}", group.getId());
            throw new ServiceException("Unable to update group with ID " + group.getId() + ".", e);
        }
    }

    public void delete(long id) {
        log.info("Delete group with ID: {}", id);

        try {
            groupRepository.deleteById(id);
        } catch (Throwable e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new ServiceException("Unable to delete group with ID " + id + ".", e);
        }
    }

    public Page<Group> findPaginated(Pageable pageable) {
        log.info("Get groups pages");

        List<Group> groups;
        try {
            groups = groupRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        } catch (Throwable e) {
            log.warn("Unable to get groups list");
            throw new ServiceException("Unable to get groups list.", e);
        }

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
