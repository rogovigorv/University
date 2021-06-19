package com.foxminded.university.dao;

import com.foxminded.university.mapper.GroupRowMapper;
import com.foxminded.university.models.Group;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.GROUP_CREATE;
import static com.foxminded.university.dao.Queries.GROUP_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_ALL;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_NAME;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_UPDATE_BY_ID;

@Repository
@Slf4j
public class GroupDao implements UniversityDao<Group> {
    private final JdbcTemplate jdbcTemplate;
    private final GroupRowMapper groupRowMapper;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupRowMapper = groupRowMapper;
    }

    public void create(Group group) throws DaoException {
        log.debug("Create group: {}", group);

        try {
            jdbcTemplate.update(GROUP_CREATE, group.getGroupName());
        } catch (DataAccessException e) {
            log.warn("Unable to create this group {}", group);
            throw new DaoException(e);
        }
    }

    @Override
    public Group getById(int id) throws DaoException {
        log.debug("Get group with ID: {}", id);

        Group group;
        try {
            group = jdbcTemplate.queryForObject(GROUP_SELECT_BY_ID, groupRowMapper, id);
        } catch (DataAccessException e) {
            log.warn("Can't get group with ID: {}", id);
            throw new DaoException(e);
        }

        return group;
    }

    public void update(Group group, int id) throws DaoException {
        log.debug("Update group: {} and ID: {}", group, id);

        try {
            jdbcTemplate.update(GROUP_UPDATE_BY_ID, group.getGroupName(), id);
        } catch (DataAccessException e) {
            log.warn("Unable to update group with ID: {}", id);
            throw new DaoException(e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        log.debug("Delete group with ID: {}", id);

        try {
            jdbcTemplate.update(GROUP_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            log.warn("Unable to delete group with ID: {}", id);
            throw new DaoException(e);
        }
    }

    public Group getByGroupName(String groupName) throws DaoException {
        log.debug("Get group with name: {}", groupName);

        Group group;
        try {
           group = jdbcTemplate.queryForObject(GROUP_SELECT_BY_NAME, groupRowMapper, groupName);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }

        return group;
    }

    public List<Group> showAll() throws DaoException {
        log.debug("Get all groups");

        List<Group> groups;
        try {
            groups = jdbcTemplate.query(GROUP_SELECT_ALL, groupRowMapper);
        } catch (DataAccessException e) {
            log.warn("Unable to get all groups");
            throw new DaoException(e);
        }

        return groups;
    }
}
