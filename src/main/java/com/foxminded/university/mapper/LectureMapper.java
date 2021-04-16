package com.foxminded.university.mapper;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LectureMapper implements RowMapper<Lecture> {
    private final Lecture lecture;
    private final TeacherDao teacherDao;
    private final GroupDao groupDao;

    @Autowired
    public LectureMapper(Lecture lecture, TeacherDao teacherDao, GroupDao groupDao) {
        this.lecture = lecture;
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
    }

    @Override
    public Lecture mapRow(ResultSet resultSet, int i) throws SQLException {

        int teacherID = resultSet.getInt("teacher_id");
        Teacher teacher = teacherDao.getById(resultSet.getInt("teacher_id"));

        int groupID = resultSet.getInt("group_id");
        Group group = groupDao.getById(groupID);

        lecture.setId(resultSet.getInt("id"));
        lecture.setTeacher(teacher);
        lecture.setLectureName(resultSet.getString("lectureName"));
        lecture.setDescription(resultSet.getString("description"));
        lecture.setGroup(group);

        return lecture;
    }
}
