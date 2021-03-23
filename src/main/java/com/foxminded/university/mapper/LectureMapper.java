package com.foxminded.university.dao;

import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Teacher;
import com.foxminded.university.models.Timetable;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureMapper implements RowMapper<Lecture> {
    @Override
    public Lecture mapRow(ResultSet resultSet, int i) throws SQLException {
        Lecture lecture = new Lecture();

        lecture.setId(resultSet.getInt("id"));
        lecture.setTeacher(resultSet.getObject("teacher_id", Teacher.class));
        lecture.setLectureName(resultSet.getString("lectureName"));
        lecture.setDescription(resultSet.getString("description"));
        lecture.setTimeTable(resultSet.getObject("time_table_id" , Timetable.class));

        return lecture;
    }
}
