package com.foxminded.university.mapper;

import com.foxminded.university.models.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        log.info("Start group mapper");

        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setGroupName(resultSet.getString("groupName"));

        return group;
    }
}
