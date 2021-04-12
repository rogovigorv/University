package com.foxminded.university.mapper;

import com.foxminded.university.Main;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureMapper implements RowMapper<Lecture> {
    @Override
    public Lecture mapRow(ResultSet resultSet, int i) throws SQLException {

        int teacherID = resultSet.getInt("teacher_id");
        Teacher teacher = Main.context.getBean(TeacherDao.class).getById(teacherID);

        Lecture lecture = new Lecture();
        lecture.setId(resultSet.getInt("id"));
        lecture.setTeacher(teacher);
        lecture.setLectureName(resultSet.getString("lectureName"));
        lecture.setDescription(resultSet.getString("description"));

        return lecture;
    }
}
