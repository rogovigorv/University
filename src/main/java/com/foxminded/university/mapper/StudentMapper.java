package com.foxminded.university.mapper;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentMapper implements RowMapper<Student> {
    private final GroupDao groupDao;

    @Autowired
    public StudentMapper(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        int groupID = resultSet.getInt("group_id");
        Group group = groupDao.getById(groupID);

        student.setId(resultSet.getInt("id"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setGroup(group);

        return student;
    }
}
