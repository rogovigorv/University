package com.foxminded.university.mapper;

import com.foxminded.university.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TeacherMapper implements RowMapper<Teacher> {
    private final Teacher teacher;

    @Autowired
    public TeacherMapper(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {

        teacher.setId(resultSet.getInt("id"));
        teacher.setFirstName(resultSet.getString("firstName"));
        teacher.setLastName(resultSet.getString("lastName"));

        return teacher;
    }
}
