package com.foxminded.university.dao;

import com.foxminded.university.mapper.GroupRowMapper;
import com.foxminded.university.models.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.GROUP_CREATE;
import static com.foxminded.university.dao.Queries.GROUP_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_NAME;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_UPDATE_BY_ID;

@Repository
@Slf4j
public class GroupDao implements UniversityDao<Group> {
    private static final String EXCEPTION_MESSAGE = "Can't find group with id: ";
    private static final String GROUP_NAME_EXCEPTION_MESSAGE = "Can't find group with name: ";

    private final JdbcTemplate jdbcTemplate;
    private final GroupRowMapper groupRowMapper;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupRowMapper = groupRowMapper;
    }

    public void create(Group group) {
        log.debug("GroupDao create method started with group: {}", group);

        try {
            jdbcTemplate.update(GROUP_CREATE, group.getId(), group.getGroupName());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Group getById(int id) {
        log.debug("GroupDao getById method started with ID: {}", id);

        return jdbcTemplate.query(GROUP_SELECT_BY_ID, groupRowMapper, new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(EXCEPTION_MESSAGE + id));
    }

    public void update(Group group, int id) {
        log.debug("GroupDao update method started with group: {} and ID: {}", group, id);

        try {
            jdbcTemplate.update(GROUP_UPDATE_BY_ID, group.getGroupName(), id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        log.debug("GroupDao delete method started with ID: {}", id);

        try {
            jdbcTemplate.update(GROUP_DELETE_BY_ID, id);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Group getByGroupName(String groupName) {
        log.debug("GroupDao getByGroupName method started with group name: {}", groupName);

        return jdbcTemplate.query(GROUP_SELECT_BY_NAME, groupRowMapper, new Object[]{groupName})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(GROUP_NAME_EXCEPTION_MESSAGE + groupName));
    }
}
