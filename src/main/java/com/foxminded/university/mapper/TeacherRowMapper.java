package com.foxminded.university.mapper;

import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class TeacherRowMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet resultSet, int i) throws SQLException {
        log.info("TeacherRowMapper mapRow method started");

        Teacher teacher = new Teacher();

        teacher.setId(resultSet.getInt("id"));
        teacher.setFirstName(resultSet.getString("firstName"));
        teacher.setLastName(resultSet.getString("lastName"));

        return teacher;
    }
}
