package com.foxminded.university.dao;

import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getInt("id"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setGroup(resultSet.getObject("group_id", Group.class));
        //как замапить поле - private List<Lecture> timeTable; ??

        return student;
    }
}
