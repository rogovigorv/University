package com.foxminded.university.mapper;

import com.foxminded.university.Main;
import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {
    @Override
    public Group mapRow(ResultSet resultSet, int i) throws SQLException {
        Lecture lecture = Main.context.getBean(LectureDao.class)
                .getByGroupId(resultSet.getInt("id"));

        Group group = new Group();
        group.setId(resultSet.getInt("id"));
        group.setGroupName(resultSet.getString("groupName"));
        group.setLecture(lecture);

        return group;
    }
}
