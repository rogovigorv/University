package com.foxminded.university.mapper;

import com.foxminded.university.Main;
import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        int groupID = resultSet.getInt("group_id");
        Group group = Main.context.getBean(GroupDao.class).getById(groupID);

        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setGroup(group);

        return student;
    }
}
