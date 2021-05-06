package com.foxminded.university.mapper;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class LectureRowMapper implements RowMapper<Lecture> {
    private final TeacherDao teacherDao;
    private final GroupDao groupDao;

    @Autowired
    public LectureRowMapper(TeacherDao teacherDao, GroupDao groupDao) {
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
    }

    @Override
    public Lecture mapRow(ResultSet resultSet, int i) throws SQLException {
        log.info("LectureRowMapper mapRow method started");

        int teacherID = resultSet.getInt("teacher_id");
        Teacher teacher = teacherDao.getById(teacherID);

        int groupID = resultSet.getInt("group_id");
        Group group = groupDao.getById(groupID);

        Lecture lecture = new Lecture();
        lecture.setId(resultSet.getInt("id"));
        lecture.setTeacher(teacher);
        lecture.setLectureName(resultSet.getString("lectureName"));
        lecture.setDescription(resultSet.getString("description"));
        lecture.setGroup(group);

        return lecture;
    }
}
