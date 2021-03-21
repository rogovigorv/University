package com.foxminded.university.dao;

import com.foxminded.university.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import static com.foxminded.university.dao.Queries.GROUP_CREATE;
import static com.foxminded.university.dao.Queries.GROUP_DELETE_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_SELECT_BY_ID;
import static com.foxminded.university.dao.Queries.GROUP_UPDATE_BY_ID;

public class GroupDao implements UniversityDao<Group> {
    private static final String EXCEPTION_MESSAGE = "Can't find group with id: ";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Group group) {
        jdbcTemplate.update(GROUP_CREATE, group.getId(), group.getGroupName());
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.query(GROUP_SELECT_BY_ID, new BeanPropertyRowMapper<>(Group.class), new Object[]{id})
                    .stream().findAny().orElseThrow(() -> new ExceptionDao(EXCEPTION_MESSAGE + id));
    }

    public void update(int id, Group group) {
        jdbcTemplate.update(GROUP_UPDATE_BY_ID, group.getGroupName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(GROUP_DELETE_BY_ID, id);
    }
}