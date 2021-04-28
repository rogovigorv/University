package com.foxminded.university.dao;

import com.foxminded.university.mapper.GroupRowMapper;
import com.foxminded.university.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import static com.foxminded.university.dao.Queries.GROUP_CREATE;
import static com.foxminded.university.dao.Queries.GROUP_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_NAME;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_UPDATE_BY_ID;

@Repository
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
            jdbcTemplate.update(GROUP_CREATE, group.getId(), group.getGroupName());
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.query(GROUP_SELECT_BY_ID, groupRowMapper, new Object[]{id})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(EXCEPTION_MESSAGE + id));
    }

    public void update(Group group, int id) {
        jdbcTemplate.update(GROUP_UPDATE_BY_ID, group.getGroupName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(GROUP_DELETE_BY_ID, id);
    }

    public Group getByGroupName(String groupName) {
        return jdbcTemplate.query(GROUP_SELECT_BY_NAME, groupRowMapper, new Object[]{groupName})
                .stream()
                .findAny()
                .orElseThrow(() -> new DaoException(GROUP_NAME_EXCEPTION_MESSAGE + groupName));
    }
}
