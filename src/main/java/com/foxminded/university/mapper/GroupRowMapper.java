package com.foxminded.university.mapper;

import com.foxminded.university.models.Group;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setGroupName(resultSet.getString("groupName"));

        return group;
    }
}
