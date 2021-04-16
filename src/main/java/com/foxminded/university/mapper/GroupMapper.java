package com.foxminded.university.mapper;

import com.foxminded.university.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GroupMapper implements RowMapper<Group> {
    private final Group group;

    @Autowired
    public GroupMapper(Group group) {
        this.group = group;
    }

    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        group.setId(resultSet.getInt("id"));
        group.setGroupName(resultSet.getString("groupName"));

        return group;
    }
}
